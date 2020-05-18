import json
import logging
import pika

print('pika version: %s' % pika.__version__)

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='rabbitmq'))

main_channel = connection.channel()
consumer_channel = connection.channel()
bind_channel = connection.channel()

main_channel.exchange_declare(exchange='twitter_exchange', exchange_type='direct')

queue = main_channel.queue_declare('', exclusive=True).method.queue
queue_tickers = main_channel.queue_declare('', exclusive=True).method.queue

main_channel.queue_bind(
    exchange='twitter_exchange', queue=queue, routing_key='tweet')


def hello():
    print('Hello world')


connection.call_later(5, hello)


def callback(_ch, _method, _properties, body):
    body = json.loads(body)['text']

    print('got tweet: %s' % body)

logging.basicConfig(level=logging.INFO)

# Note: consuming with automatic acknowledgements has its risks
#       and used here for simplicity.
#       See https://www.rabbitmq.com/confirms.html.
consumer_channel.basic_consume(queue, callback, auto_ack=True)

try:
    consumer_channel.start_consuming()
finally:
    connection.close()
