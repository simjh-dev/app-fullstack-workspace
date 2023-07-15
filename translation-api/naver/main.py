import os
import urllib.request
import json

from dotenv import load_dotenv, find_dotenv
_ = load_dotenv(find_dotenv()) # read local .env file

client_id = os.getenv("TRANSLATION_NAVER_API_CLIENT_ID")
client_secret = os.getenv("TRANSLATION_NAVER_API_CLIENT_SECRET")


def translate_text(source, target, encText):
    data = f"source={source}&target={target}&text={encText}"
    url = "https://openapi.naver.com/v1/papago/n2mt"
    request = urllib.request.Request(url)
    request.add_header("X-Naver-Client-Id", client_id)
    request.add_header("X-Naver-Client-Secret", client_secret)
    response = urllib.request.urlopen(request, data=data.encode("utf-8"))
    rescode = response.getcode()
    if(rescode==200):
        response_body = response.read().decode("utf-8")

        # print(response_body)
        # {
        #     "message": {
        #         "result": {
        #             "srcLangType": "en",
        #             "tarLangType": "ko",
        #             "translatedText": "구름",
        #             "engineType": "PRETRANS"
        #         },
        #         "@type": "response",
        #         "@service": "naverservice.nmt.proxy",
        #         "@version": "1.0.0"
        #     }
        # }

        result = json.loads(response_body)
        print(f"Text: {encText}")
        print(f"Translation: {result['message']['result']['translatedText']}")
        print(f"Source language: {source}")

    else:
        print(f"Error code: {rescode} \n {response}")

source = urllib.parse.quote("en")
target = urllib.parse.quote("ko")
encText = urllib.parse.quote("cloud")

translate_text(source, target, encText)
# Text: cloud
# Translation: 구름
# Source language: en