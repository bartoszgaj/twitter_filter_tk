import json
import random
import pika
import tweepy

TWITTER_APP_KEY = '???'
TWITTER_APP_SECRET = '???'
TWITTER_KEY = '??'
TWITTER_SECRET = '???'

print('pika version: %s' % pika.__version__)
print('tweepy version: %s' % tweepy.__version__)

filter_list = ["#koronawirus", "wybory2020"]

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='rabbitmq'))
main_channel = connection.channel()

main_channel.exchange_declare(exchange='twitter_exchange', exchange_type='direct')

print("Rabbit connect successful")

auth = tweepy.OAuthHandler(TWITTER_APP_KEY, TWITTER_APP_SECRET)
auth.set_access_token(TWITTER_KEY, TWITTER_SECRET)
api = tweepy.API(auth)

class StreamListener(tweepy.StreamListener):
    def on_status(self, status):
        print("Downloaded tweet: %s", status.text)
        json_str = json.dumps(status._json)
        main_channel.basic_publish(
            exchange='twitter_exchange',
            routing_key='tweet',
            body=json_str,
            properties=pika.BasicProperties(content_type='application/json'))
        print("Sent tweet: %s" % status.text)

    def on_error(self, status_code):
        if status_code == 420:
            print("Closing with error 420")
            return False

stream_listener = StreamListener()
stream = tweepy.Stream(auth=api.auth, listener=stream_listener)
stream.filter(track=filter_list)

connection.close()
