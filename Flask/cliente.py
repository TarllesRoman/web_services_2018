import json
import os
import requests

URL = "http://localhost:8080/arquivos/"

def help():
    return requests.get(URL).text

def list_json():
    return requests.get(URL+'json').text

def list_xml():
    return requests.get(URL+'xml').text

def get_file(file):
    return requests.get(URL+str(file)).text

def delete_all():
    r = requests.delete(URL)
    return r['msg']

def delete_file(file):
    return requests.delete(URL+file).text

def put_file(name, content):
    return requests.put(URL+name, data={"conteudo":content})

operacoes = [help, list_json, list_xml, get_file, delete_all, delete_file, put_file]

def menu():
    print('1- HELP')
    print('2- LISTA JSON')
    print('3- LISTA XML')
    print('4- GET FILE')
    print('5- DELETE ALL')
    print('6- DELETE FILE')
    print('7- PUT FILE')
    print('0- SAIR')

    return int(input('Escolha uma opção: '))

def main():
    op = menu()
    while op != 0:
        if(op != 4 and op!= 6 and op != 7):
            print(operacoes[op-1]())
        elif(op != 7):
            name = str(input('Digite o nome do arquivo: '))
            print(operacoes[op-1](name))
        else:
            name = str(input('Digite o nome do arquivo: '))
            data = str(input('Digite o conteudo do arquivo: '))
            print(operacoes[6](name,data))

        input("DIGITE QUALQUER TECLA PARA CONTINUAR...")
        os.system('clear')
        op = menu()

if __name__ == "__main__":
    main()
        