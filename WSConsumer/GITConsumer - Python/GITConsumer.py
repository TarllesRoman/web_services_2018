#coding: utf-8

import json
import urllib2

def do_request(req_url):
	try:
		f = open(req_url.replace('/','-'), 'r')
		text = f.read()
		f.close()
		return text
	except Exception as i:
		print i
		try:
			r = urllib2.urlopen(req_url).read()
			f = open(req_url.replace('/','-'),'w')
			f.write(r)
			f.close()
			return r
		except Exception as e:
			print e
			return ""


semiURL = "https://api.github.com/users/"

userName = raw_input("Digite o nome de usuário: ")

resposta = do_request(semiURL+userName)
dados = json.loads(resposta)

result = "Usuario da consulta: " + str(dados["name"]) + "\n\n"
result += str(dados["followers"]) + " seguidores:\n\n"

resposta = do_request(dados["followers_url"])
dados = json.loads(resposta)

for d in dados:
	resp = do_request(d["url"])
	datas = json.loads(resp)
	
	if(datas["name"] != None):
		result += datas["name"]
		result += "\n"
	
	resp = do_request(d["repos_url"])
	datas = json.loads(resp)
	
	for dt in datas:
		result += "\t" + str(dt["name"]) + "\n"
		
print result
