FROM python:3.8-alpine
ENV PYTHONPATH /usr/src/app

ENV PYTHONUNBUFFERED=1

WORKDIR /usr/src/app
COPY requirements.txt .
RUN pip install -r requirements.txt

COPY consumer.py .

CMD ["python3", "/usr/src/app/consumer.py"]
