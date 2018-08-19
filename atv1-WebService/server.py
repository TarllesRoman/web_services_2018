#coding: utf-8

server_port = 19919

from socket import *
import subprocess
import commands
import json

serverSocket = socket(AF_INET,SOCK_STREAM)
serverSocket.bind(('', server_port))
serverSocket.listen(1)

def showInfos(jsn_data, message):
	stri = "A turma "+str(jsn_data.get("id"))
	stri += " de "+str(jsn_data.get("ano"))
	stri += " do curso "+jsn_data.get("curso") 
	
	qtd_umat = message.count("false")
	qtd_mat = message.count("true")
	
	stri += " possui "+str(qtd_umat+qtd_mat)
	stri += " alunos, dos quais "+str(qtd_mat)
	stri += " estao devidamente matriculados."
	
	print stri

while True:
	print "Server pronto para aceitar conexao"
	
	conexao, cliente = serverSocket.accept()
	print "Conexao aceita"
	
	while True:
		message = conexao.recv(2048)
		
		if(message==""):
			print "Conexao encerrada"
			conexao.close()
			break
		
		try:
			data = json.loads(message[message.find("{"):])
			showInfos(data, message[message.find("{"):])
		except:
			continue

	conexao.close()
	
serverSocket.close()
