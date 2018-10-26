import json
import os
import requests

import xml.etree.ElementTree as ET

from flask import Flask
from flask import request

app = Flask(__name__)

@app.route('/arquivos/', methods=['GET'])
def arquivos():
    return json.dumps({'GET /arquivos/json':'Retorna um json com o nome de todos os arquivos',
            'GET /arquivos/xml':'Retorna um xml com o nome de todos os arquivos',
            'GET /arquivos/nome_arquivo':'Retorna o conteudo textual do arquivo',
            'DELETE /arquivos':'Remove todos os arquivos',
            'DELETE /arquivos/nome_arquivo':'Remove o arquivo com nomeado como: nome_arquivo',
            'PUT /arquivos/nome_arquivo':'Cria um arquivo com esse nome e conteudo, caso ja exista atualiza seu conteudo'})

@app.route('/arquivos/json', methods=['GET'])
def arquivos_json():
    list_files = os.listdir('./Arquivos')
    return json.dumps({'Arquivos':list_files})

@app.route('/arquivos/xml', methods=['GET'])
def arquivos_xml():
    list_files = os.listdir('./Arquivos')

    root = ET.Element('files')

    for file in list_files:
        sub_element = ET.SubElement(root,'file')
        sub_element.text = file
        print(file)

    return ET.tostring(root)
    '''return app.response_class(ET.tostring(root), mimetype='application/xml')'''

@app.route('/arquivos/<arquivo>', methods=['GET'])
def arquivos_get(arquivo):
    try:
        arq = open('./Arquivos/'+arquivo, 'r')
        content = arq.read()
        arq.close()
        return content
    except:
        return json.dumps({'cod':404, 'msg':'Arquivo nao encontrado'})

@app.route('/arquivos/', methods=['DELETE'])
def arquivos_deletall():
    listd = os.listdir('Arquivos')
    for f in listd:
        os.remove('./Arquivos/'+f)
    return json.dumps({'cod':200,'msg':'Todos os arquivos foram deletados'})

@app.route('/arquivos/<arquivo>', methods=['DELETE'])
def arquivos_delete(arquivo):
    try:
        os.remove('./Arquivos/'+arquivo)
        return json.dumps({'cod':200,'msg':'Arquivo '+arquivo+' deletado'})
    except:
        return json.dumps({'cod':404, 'msg':'Arquivo nao encontrado'})



@app.route('/arquivos/<arquivo>', methods=['PUT'])
def arquivos_put(arquivo):
    try:
        arq = open('./Arquivos/'+arquivo, 'w')
        print('qualquer')
        content = request.form['conteudo']
        print(content)
        arq.write(content)
        arq.close()
        return json.dumps({'cod':200, 'msg':'Arquivo criado com sucesso'})
    except:
        return json.dumps({'cod':400, 'msg':'Falha ao criar o arquivo'})



if __name__ == "__main__":
    app.run(debug=True, port=8080)
