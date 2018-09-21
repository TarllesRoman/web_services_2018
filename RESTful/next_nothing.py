#coding: utf-8

import requests
import webbrowser

url = 'http://www.pythonchallenge.com/pc/def/linkedlist.php?nothing='

r = requests.get(url+'12345')

ult_numero = '12345'

while(True):
    if(r.status_code == requests.codes.ok):
        text = r.text;
        print(text)

        try:
            spl = text.split('next nothing is')
            nn = spl[1]
            ult_numero = nn
        except IndexError:
            if ('Divide by two' in text):
                nn = str(int(ult_numero) / 2)
                ult_numero = nn

            if ('peak.html' in  text):
                webbrowser.open('http://www.pythonchallenge.com/pc/def/'+text)
                break

        r = requests.get(url+nn)
