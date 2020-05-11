const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const fetch = require('node-fetch');

const PORT = process.env.PORT || 3000;

const app = express(); 

app.use(bodyParser.json());

app.use(cors());

app.get('/', function(req,res) {
    res.send('Hello from server');
})

// app.get('/geocode', async (req,res) => {
//     console.log(req.body);
//     res.send('Hello from server');
// })

app.get('/geocode/:address', async function(req,res){
    console.log(req.params)
    const address = req.params.address.split(',');
    // var street = req.body.street;
    // var city = req.body.city;
    // var state = req.body.state;
    var street = address[0];
    var city = address[1];
    var state = address[2];
    // var street = "Laurie Ln";
    // var city = "Thousand Oaks";
    // var state = "California";
    const geocode_apikey = "AIzaSyAlr1MpY6Iesx32PhwQTeFZzLMTBa3REl0";
    var uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + street + "," + city + "," + state + "&key=" + geocode_apikey;
    var endpoint = encodeURI(uri);
    const fetch_response = await fetch(endpoint);
    const json = await fetch_response.json();
    // var jsonData = JSON.parse(json);
    // console.log(json);
    // console.log(json);
    res.status(200).send(json);
});

app.get('/forecast/:latlon', async function(req,res){
    console.log(req.params)
    const latlon = req.params.latlon.split(',');
    var lat = latlon[0];
    var long = latlon[1];
    const forecast_apikey = "797fe7f0bdb17655c868cd29e5fa7fe5";
    var uri = "https://api.darksky.net/forecast/" + forecast_apikey + "/" + lat + "," + long;
    var endpoint = encodeURI(uri);
    const fetch_response = await fetch(endpoint);
    const json = await fetch_response.json();
    // var jsonData = JSON.stringify(json);
    // console.log(json);
    // console.log(json);
    res.status(200).send(json);
});
app.get('/forecastmodal/:latlontime', async function(req,res){
    console.log(req.params)
    const latlon = req.params.latlon.split(',');
    var lat = latlon[0];
    var long = latlon[1];
    var time = latlon[2];
    const forecast_apikey = "797fe7f0bdb17655c868cd29e5fa7fe5";
    var uri = "https://api.darksky.net/forecast/" + forecast_apikey + "/" + lat + "," + long + "," + time;
    var endpoint = encodeURI(uri);
    const fetch_response = await fetch(endpoint);
    const json = await fetch_response.json();
    // var jsonData = JSON.stringify(json);
    // console.log(json);
    // console.log(json);
    res.status(200).send(json);
});

app.get('/autocomplete/:chars', async function(req,res){
    // console.log(req.params.chars);
    var cityString = req.params.chars;
    const autocomplete_apikey = "AIzaSyCOXPDJRvPLiIzyaQKABZVvBQ_TP2X7o68";
    var uri = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + cityString + "&types=(cities)&language=en&key=" 
                + autocomplete_apikey;
    var endpoint = encodeURI(uri);
    const fetch_response = await fetch(endpoint);
    const json = await fetch_response.json();
    res.status(200).send(json);
});

app.listen(PORT, function(){
    console.log("Server running on localhost:" + PORT);
});

// function api_call(endpoint) {
//     var xhr = new XMLHttpRequest();
//     xhr.open('GET', endpoint, true);
//     xhr.send();
//     jsonObj= JSON.parse(xhr.responseText);
//     return jsonObj;
// }

// const parseJsonAsync = (jsonString) => {
//     return new Promise(resolve => {
//       setTimeout(() => {
//         resolve(JSON.parse(jsonString))
//       })
//     })
//   }