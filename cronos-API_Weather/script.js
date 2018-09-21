/* https://api.openweathermap.org/data/2.5/weather?q=Barbacena&appid=80c46a1e81cf2f03cc7aa4950b6760f5
appid=*/

const api_key = '80c46a1e81cf2f03cc7aa4950b6760f5';
const url_img = 'http://openweathermap.org/img/w/';

var e_map = document.getElementById('map');

var radio_JSON = document.querySelector('#radio_JSON');
var radio_XML = document.querySelector('#radio_XML');
var ipt_cidade = document.querySelector('#ipt_cidade');
var btn_buscar = document.querySelector('#btn_buscar');

var clima_ic = document.querySelector('#icone_clima');
var nome_cidade = document.querySelector('#nome_cid');
var coord = document.querySelector('#coordenadas');
var t_atual = document.querySelector('#temp_atual');
var t_min = document.querySelector('#temp_min');
var t_max = document.querySelector('#temp_max');
var pressure = document.querySelector('#pressure');
var dawn = document.querySelector('#dawn');
var dusk = document.querySelector('#dusk');

btn_buscar.onclick = function(){
	if(radio_JSON.checked == true){
		searchJSON( removeAcento(ipt_cidade.value) );
		ipt_cidade.value = '';
	}else{
		searchXML( removeAcento(ipt_cidade.value) );
		ipt_cidade.value = '';
	}
}

function searchJSON(cidade){
	var req = new XMLHttpRequest();
	req.onloadend = function(){
		resp_json = JSON.parse(req.responseText);
		if(resp_json.cod == '404')
			not_found();
		else
			loadJSON(resp_json);
	}
	req.open('GET', 'https://api.openweathermap.org/data/2.5/weather?q='+cidade+'&appid='+api_key);
	req.send(null);
}

function loadJSON(json){
	console.log(json);
	
	clima_ic.src = url_img + json.weather[0].icon + '.png';
	nome_cidade.innerHTML = json.name;
	coord.innerHTML = json.coord.lat + ', ' + json.coord.lon;
	
	t_atual.innerHTML = kelvin_celcius(json.main.temp) + 'ºC'; 
	t_min.innerHTML = kelvin_celcius(json.main.temp_min) + 'ºC';
	t_max.innerHTML = kelvin_celcius(json.main.temp_max) + 'ºC';
	pressure.innerHTML = json.main.pressure + ' hpa';
	dawn.innerHTML = sec_hora(json.sys.sunrise);
	dusk.innerHTML = sec_hora(json.sys.sunset);
	
	loadMap(json.coord.lat, json.coord.lon);
}

function searchXML(cidade){
	var req = new XMLHttpRequest();
	req.onreadystatechange = function(){
		if(req.readyState == 4){
			if(req.status == 200)
				loadXML(req.responseXML)
			else
				not_found();
		}
	}
	url = 'https://api.openweathermap.org/data/2.5/weather?q='+cidade+'&appid='+api_key+'&mode=xml';
	req.open('GET', url, true);
	req.send(null);
}

function loadXML(xml){
	console.log(xml);
	
	city = xml.getElementsByTagName('city')[0];
	crd = city.getElementsByTagName('coord')[0];
	sun = city.getElementsByTagName('sun')[0];
	weather = xml.getElementsByTagName('weather')[0];
	temp = xml.getElementsByTagName('temperature')[0];
	pres = xml.getElementsByTagName('pressure')[0];
	
	nome_cidade.innerHTML = city.getAttribute('name');
	clima_ic.src = url_img + weather.getAttribute('icon') + '.png';
	coord.innerHTML = crd.getAttribute('lat') + ', ' + crd.getAttribute('lon');
	t_atual.innerHTML = kelvin_celcius(temp.getAttribute('value')) + 'ºC';
	t_min.innerHTML = kelvin_celcius(temp.getAttribute('min')) + 'ºC';
	t_max.innerHTML = kelvin_celcius(temp.getAttribute('max')) + 'ºC';
	pressure.innerHTML = pres.getAttribute('value')+ ' '+pres.getAttribute('unit'); 
	dawn.innerHTML = stamp_hora(sun.getAttribute('rise'));
	dusk.innerHTML = stamp_hora(sun.getAttribute('set'));

	loadMap(crd.getAttribute('lat'), crd.getAttribute('lon'));
	
}

function loadMap(latitude, longitude){
	var map = new google.maps.Map(document.getElementById('map'), {
		center: {lat: parseFloat(latitude), lng: parseFloat(longitude)},
		zoom: 11
	});
}

function not_found(){
	alert("Cidade não encontrada!");
}

function sec_hora(sec){
    var date = new Date(sec * 1000);
    return date.getHours() +':'+date.getMinutes();
}

function stamp_hora(stamp){
	return stamp.split('T')[1];
}

function kelvin_celcius(kel){
    return Math.round(kel - 273.15);
}

function removeAcento(text){
    text = text.toLowerCase();
    text = text.replace(new RegExp('[ÁÀÂÃ]','gi'), 'a');
    text = text.replace(new RegExp('[ÉÈÊ]','gi'), 'e');
    text = text.replace(new RegExp('[ÍÌÎ]','gi'), 'i');
    text = text.replace(new RegExp('[ÓÒÔÕ]','gi'), 'o');
    text = text.replace(new RegExp('[ÚÙÛ]','gi'), 'u');
    text = text.replace(new RegExp('[Ç]','gi'), 'c');
    return text;
}

searchJSON('Barbacena');


