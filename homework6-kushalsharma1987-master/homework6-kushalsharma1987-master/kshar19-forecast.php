<!DOCTYPE html>
<head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <meta http-equiv="Content-Type" content="text/html; application/json charset=windows-1252">
    <style type="text/css">
    body{
        text-align:center;
    }
    .search-box{
        display:inline-block;
        border-radius: 10px;
        background-color: rgb(48, 160, 4);
        color: white;
        top:50px;
        width:700px;
    }
    #search-heading{
        font-style:italic;
        font-size:40px;
        margin:2px; 
        padding:2px;
        text-align:center;
    }
    .address-box{
        text-align:left;
        width:400px;
    }
    input[type="text"] select{
        width:150px;
        left:20px;
        border-radius: 10px;
    }
    form{
        width:80%;
        margin: 0 auto;
    }
    label{
        width:30%;
        text-align:right;
    }
    #forecast{
        display:inline-block; 
        text-align:center; 
        margin: 0 auto;
    }

    #curr_forecast{
        display:inline-block; 
        text-align:left; 
        line-height:2px; 
        margin:20px auto;
        padding: 20px; 
        border-radius: 10px;
        background-color:deepskyblue;
        color: white;
    }

    #day_forecast_card{
        display:inline-block; 
        text-align:left;
        padding:0px 20px; 
        border-radius: 10px;
        background-color:rgb(132, 215, 230);
        color: white;
        margin: 0 auto;
    }

    #day_forecast_chart{
        display:inline-block; 
        text-align:left; 
        color: white;
        margin: 0 auto;
    }

    p #curr-forecast{
        margin: 2px;
        padding: 2px;
    }
    #day_forecast table, #day_forecast tr, #day_forecast th  {
        border:2px solid rgb(11, 119, 138); 
        color: white;
        font-style: bold;
        padding-left: 5px;
        padding-right: 5px;
    }
    #day_forecast_table table, #day_forecast_table tr, #day_forecast_table th  {
        padding: 0px;
        border-collapse:collapse;
    }
    </style>
</head>
<body onload="checkRefresh();">
<div class="search-box" style="text-align:center;padding-bottom:20px;">
    <p id="search-heading">Weather Search</p>
    <div class="address-box" style="width:50%;float:left;margin: 0 auto;">
    <form style="margin:0 auto;" method="GET" action="<?php $_SERVER['PHP_SELF'];?>">
        <p><label style="display: inline-block;width: 45px;text-align: left;" for="street"><b>Street </b></label><input style="border:0px;padding:5px;" type="text" id="street" name="street" size=20
            value="<?php echo isset($_GET["street"]) ? $_GET["street"] : "" ?>"></p>
        <p><label style="display: inline-block;width: 45px;text-align: left;" for="city"><b>City </b></label><input style="border:0px;padding:5px;" type="text" id="city" name="city" size=20
            value="<?php echo isset($_GET["city"]) ? $_GET["city"] : "" ?>"></p>
        <p><label style="display: inline-block;width: 45px;text-align: left;" for="state"><b>State </b></label><select style="border-radius:5px;border: 0px;" name="state" id="state">
            <option selected=selected value="">State</option>
            <option disabled value="">...............................................</option>
            <?php
            $JSONstates = '[	{	"Abbreviation":"AL",	"State":"Alabama"	},	{	"Abbreviation":"AK",	"State":"Alaska"	},	{	"Abbreviation":"AZ",	"State":"Arizona"	},	{	"Abbreviation":"AR",	"State":"Arkansas"	},	{	"Abbreviation":"CA",	"State":"California"	},	{	"Abbreviation":"CO",	"State":"Colorado"	},	{	"Abbreviation":"CT",	"State":"Connecticut"	},	{	"Abbreviation":"DE",	"State":"Delaware"	},	{	"Abbreviation":"DC",	"State":"District Of Columbia"	},	{	"Abbreviation":"FL",	"State":"Florida"	},	{	"Abbreviation":"GA",	"State":"Georgia"	},	{	"Abbreviation":"HI",	"State":"Hawaii"	},	{	"Abbreviation":"ID",	"State":"Idaho"	},	{	"Abbreviation":"IL",	"State":"Illinois"	},	{	"Abbreviation":"IN",	"State":"Indiana"	},	{	"Abbreviation":"IA",	"State":"Iowa"	},	{	"Abbreviation":"KS",	"State":"Kansas"	},	{	"Abbreviation":"KY",	"State":"Kentucky"	},	{	"Abbreviation":"LA",	"State":"Louisiana"	},	{	"Abbreviation":"ME",	"State":"Maine"	},	{	"Abbreviation":"MD",	"State":"Maryland"	},	{	"Abbreviation":"MA",	"State":"Massachusetts"	},	{	"Abbreviation":"MI",	"State":"Michigan"	},	{	"Abbreviation":"MN",	"State":"Minnesota"	},{	"Abbreviation":"MS",	"State":"Mississippi"	},{	"Abbreviation":"MO",	"State":"Missouri"	},{	"Abbreviation":"MT",	"State":"Montana"	},{	"Abbreviation":"NE",	"State":"Nebraska"	},{	"Abbreviation":"NV",	"State":"Nevada"	},{	"Abbreviation":"NH",	"State":"New Hampshire"	},{	"Abbreviation":"NJ",	"State":"New Jersey"	},{	"Abbreviation":"NM",	"State":"New Mexico"	},{	"Abbreviation":"NY",	"State":"New York"	},{	"Abbreviation":"NC",	"State":"North Carolina"	},{	"Abbreviation":"ND",	"State":"North Dakota"	},{	"Abbreviation":"OH",	"State":"Ohio"	},{	"Abbreviation":"OK",	"State":"Oklahoma"	},{	"Abbreviation":"OR",	"State":"Oregon"	},{	"Abbreviation":"PA",	"State":"Pennsylvania"	},{	"Abbreviation":"RI",	"State":"Rhode Island"	},{	"Abbreviation":"SC",	"State":"South Carolina"	},{	"Abbreviation":"SD",	"State":"South Dakota"	},{	"Abbreviation":"TN",	"State":"Tennessee"	},{	"Abbreviation":"TX",	"State":"Texas"	},{	"Abbreviation":"UT",	"State":"Utah"	},{	"Abbreviation":"VT",	"State":"Vermont"	},{	"Abbreviation":"VA",	"State":"Virginia"	},{	"Abbreviation":"WA",	"State":"Washington"	},{	"Abbreviation":"WV",	"State":"West Virginia"	},{	"Abbreviation":"WI",	"State":"Wisconsin"	},	{	"Abbreviation":"WY",	"State":"Wyoming"	}	]';
            $Arraystates = json_decode($JSONstates, true);
            foreach ($Arraystates as $key => $value) {
                echo '<option value="' . $value["State"] . '"';
                if($_GET['state']==$value["State"]) echo 'selected="selected"';
                echo '>' . $value["State"] . '</option>';
            }
            ?>
        </select></p>
        <br><br>
        <div style="float:right;">
        <span><input style="border-radius:5px;border:0px;" type="button" onclick="validateAndSubmit()" name="submit" id="submit" value="search"></span>
        <span><input style="border-radius:5px;border:0px;" type="button" onclick="clearContent()" name="clear" id="clear" value="clear"></span>
        </div>
    </form></div>
    <div style="float:left;width:5px;height:160px;background-color:white;"></div>
    <div class="currloc-box" style="text-align:right;">
        <form method="GET" action="<?php $_SERVER['PHP_SELF'];?>">
        <p><label for="currloc"></label><input style="border-radius:5px; border:0px;font-size:15px;margin:0px auto;" type="checkbox" onchange="currLocCheckBox();" id="currloc" name="currloc" 
            value="true" <?php if(isset($_GET['currloc'])) echo "checked='checked'"; ?>><b>Current Location</b></p>
        </form> 
    </div>
    <br><br>
</div>
<br><br>
<div id="forecast">
<?php
        
        if(isset($_GET["submit"])) {
        
            $lat_long = geocode($_GET["street"],$_GET["city"],$_GET["state"]);

            if ($lat_long == NULL){
                error();
                return;
            }

            $forecast_data = forecast($lat_long);

            if ($forecast_data == NULL){
                error();
                return;
            }

            display_current_forecast($forecast_data);
           
        }

        if (isset($_GET["time"])){
            
            $time = $_GET["time"];

            $lat_long = array("lat"=>$_GET["lat"],"long"=>$_GET["long"]);

            if ($lat_long == NULL){
                error();
                return;
            }
           
            $daily_forecast_data = daily_forecast($lat_long, $time);

            if ($daily_forecast_data == NULL){
                error();
                return;
            }
            
            display_daily_forecast($daily_forecast_data);
        } else if (isset($_GET["currloc"])){

            
            $lat_long = array("lat"=>$_GET["lat"],"long"=>$_GET["long"]);
                       
            if ($lat_long == NULL){
                error();
                return;
            }

            $forecast_data = forecast($lat_long);

            if ($forecast_data == NULL){
                error();
                return;
            }

            display_current_forecast($forecast_data);

            // disableSearchBox();
        }

        function alert($message){
            echo '<script type="text/javascript">alert("' . $message . '")</script>';
        }

        function error(){
            echo '<script type="text/javascript">document.getElementById("forecast").innerHTML = "Please check the input address";</script>';
            echo '<script type="text/javascript">document.getElementById("forecast").style.border = "2px solid grey";</script>';
            echo '<script type="text/javascript">document.getElementById("forecast").style.padding = "0px 100px";</script>';
        }

        function disableSearchBox(){
            echo '<script type="text/javascript">document.getElementById("street").setAttribute("disabled", "true");</script>';
            echo '<script type="text/javascript">document.getElementById("city").setAttribute("disabled", "true");</script>';
            echo '<script type="text/javascript">document.getElementById("state").setAttribute("disabled", "true");</script>';
        }

        function geocode($street, $city, $state) {
            $geocode_apikey = "AIzaSyDJghrhrH7XfSRnpKqK75PicX7jvTrtyx4";
            $address= str_replace(" ", "+", urlencode($street . $city . $state));
            $url = "https://maps.googleapis.com/maps/api/geocode/xml?address=$address&key=$geocode_apikey";
            $xmlresponse .= file_get_contents($url);
            $xmldata = simplexml_load_string($xmlresponse) or die("Error: Cannot create object");
            if($xmldata->status =='OK'){
                $latitude= $xmldata->result->geometry->location->lat;
                $longitude= $xmldata->result->geometry->location->lng;
                return array("lat"=>$latitude,"long"=>$longitude);
            } else {
                return false;
            }
        }
        function forecast($lat_long){
            $forecast_apikey = "797fe7f0bdb17655c868cd29e5fa7fe5";
            $latitude = $lat_long["lat"];
            $longitude = $lat_long["long"];
            $url = "https://api.forecast.io/forecast/$forecast_apikey/$latitude,$longitude?exclude=minutely,hourly,alerts,flags";
            $jsonresponse .= file_get_contents($url);
            $forecast_data = json_decode($jsonresponse, true);
            return $forecast_data;
        }
        function daily_forecast($lat_long, $time){
            $forecast_apikey = "797fe7f0bdb17655c868cd29e5fa7fe5";
            $latitude = $lat_long["lat"];
            $longitude = $lat_long["long"];
            $url = "https://api.darksky.net/forecast/$forecast_apikey/$latitude,$longitude,$time?exclude=minutely";
            $jsonresponse .= file_get_contents($url);
            $daily_forecast_data = json_decode($jsonresponse, true);
            return $daily_forecast_data;
        }
        function display_current_forecast($forecast_data){
            echo '<div id="curr_forecast">';
            if (isset($_GET["city"])){
                echo '<p style="font-style:bold;font-size:30px;margin:20px 0px;"><b>' . $_GET["city"] . '</b></p>';
            } else if (isset($_GET["currloc"])){
                echo '<p style="font-style:bold;font-size:30px;margin:20px 0px;"><b>' . $_GET["currcity"] . '</b></p>';
            }
            echo '<p style="font-size:12px;padding:0px;">' . $forecast_data["timezone"] . '</p>';
            echo '<p style="font-style:bold;font-size:60px;margin:15px 0px;"><b>' . round($forecast_data["currently"]["temperature"]) . '</b><img src="https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png" alt="degree" style="margin-bottom:40px; width:10px; height:10px;">' . '<span style="font-size:30px;"><b>F</b></span></p>';
            echo '<p style="font-style:bold;font-size:20px;margin:auto;padding:20px 0px;"><b>' . $forecast_data["currently"]["summary"] . '</b></p>';
            echo '<div style="text-align:center;">';
            if (array_key_exists("humidity",$forecast_data["currently"])){
                echo '<span style="text-aling:center;display:inline-block; font-size:20px;font-style:bold;" title="Humidity">' . '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png" alt="Humidity" style="padding-bottom:10px;width:20px; height:20px;"><br><b>' . $forecast_data["currently"]["humidity"] . '</b></span>';
            }
            if (array_key_exists("pressure",$forecast_data["currently"])){
                echo '<span style="margin-left:30px;align-item:center;display:inline-block; font-size:20px;font-style:bold;" title="Pressure">' . '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png" alt="Pressure" style="padding-bottom:10px;width:20px; height:20px;"><br><b>' . $forecast_data["currently"]["pressure"] . '</b></span>';
            }
            if (array_key_exists("windSpeed",$forecast_data["currently"])){
                echo '<span style="margin-left:30px;text-aling:center;display:inline-block; font-size:20px;font-style:bold;" title="Wind Speed">' . '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png" alt="Wind Speed" style="padding-bottom:10px;width:20px; height:20px;"><br><b>' . $forecast_data["currently"]["windSpeed"] . '</b></span>';
            }
            if (array_key_exists("visibility",$forecast_data["currently"])){
                echo '<span style="margin-left:30px;text-aling:center;display:inline-block; font-size:20px;font-style:bold;" title="Visibility">' . '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png" alt="Visiblity" style="padding-bottom:10px;width:20px; height:20px;"><br><b>' . $forecast_data["currently"]["visibility"] . '</b></span>';
            }
            if (array_key_exists("cloudCover",$forecast_data["currently"])){
                echo '<span style="margin-left:30px;text-aling:center;display:inline-block; font-size:20px;font-style:bold;" title="Cloud Cover">' . '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png" alt="Cloud Cover" style="padding-bottom:10px;width:20px; height:20px;"><br><b>' . $forecast_data["currently"]["cloudCover"] . '</b></span>';
            }
            if (array_key_exists("ozone",$forecast_data["currently"])){
                echo '<span style="margin-left:30px;text-aling:center;display:inline-block; font-size:20px;" title="Ozone">' . '<img src="https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png" alt="Ozone" style="padding-bottom:10px;width:20px; height:20px;"><br><b>' . $forecast_data["currently"]["ozone"] . '</b></span>';
            }
            echo '</div>';
            echo "<br><br>";
            echo "</div>";
            echo "<br>";

            echo '<div id="day_forecast">';
            echo '<table id="forecast_table" align="center" style="color=white; border-collapse:collapse; background-color: rgb(115, 185, 241); ">';
            echo '<tr><th>Date</th><th>Status</th><th>Summary</th><th>TemperatureHigh</th><th>TemperatureLow</th><th>Wind Speed</th>';
            $index = 0;
            foreach($forecast_data["daily"]["data"] as $day){
                $index++;
                echo "<tr>";
                echo "<th>" . gmdate("Y-m-d", $day["time"]) . "</th>";
                switch($day["icon"]) {
                    case "clear-day":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png";
                        break;
                    case "clear-night":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png";
                        break;
                    case "rain":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-04-512.png";
                        break;
                    case "snow":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-19-512.png";
                        break;
                    case "sleet":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-07-512.png";
                        break;
                    case "wind":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png";
                        break;
                    case "fog":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png";
                        break;
                    case "cloudy":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-01-512.png";
                        break;
                    case "partly-cloudy-day":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png";
                        break;
                    case "partly-cloudy-night":
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-02-512.png";
                        break;
                    default:
                        $icon = "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-12-512.png";
                }
                echo '<th><img src="' . $icon . '" style="width:40px; height:40px"></th>';
                echo '<th><a href="#" onclick="loadDailyForecast(this);" style="padding:0 20px;color:white;text-decoration:none">' . $day["summary"] . "</a></th>";
                echo "<th>" . $day["temperatureHigh"] . "</th>";
                echo "<th>" . $day["temperatureLow"] . "</th>";
                echo "<th>" . $day["windSpeed"] . "</th>";
                echo "</tr>";
            }        
            echo "</table></div>";
        }

        function display_daily_forecast($daily_forecast_data){
            echo "<h1>Daily Weather Detail</h1>";
            echo '<div id="day_forecast_card">';
            echo '<div style="float:left;margin:50px 0 0 0">';
            echo '<p style="width:70px;font-size:30px;margin:0 0 10px 0;"><b>' . $daily_forecast_data["currently"]["summary"] . '</b></p>';
            echo '<p style="font-size:80px;margin:0 auto;"><b>' . round($daily_forecast_data["currently"]["temperature"]) . '</b><img src="https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png" alt="degree" style="margin-bottom:50px; width:10px; height:10px;">' . '<span style="font-size:60px;"><b>F</b></p>';
            echo '</div>';
            switch($daily_forecast_data["currently"]["icon"]) {
                case "clear-day":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";
                    break;
                case "clear-night":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";
                    break;
                case "rain":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png";
                    break;
                case "snow":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png";
                    break;
                case "sleet":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png";
                    break;
                case "wind":
                    $icon = "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png";
                    break;
                case "fog":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png";
                    break;
                case "cloudy":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png";
                    break;
                case "partly-cloudy-day":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";
                    break;
                case "partly-cloudy-night":
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";
                    break;
                default:
                    $icon = "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";
            }
            echo '<img src="' . $icon . '" style="margin-left:50px ;width:200px; height:200px;text-align:right;">';
            $precipIntensity = $daily_forecast_data["currently"]["precipIntensity"];
            switch(true) {
                case ($precipIntensity <= 0.001):
                    $precipDisplay = "None";
                    break;
                case ($precipIntensity <= 0.015):
                    $precipDisplay = "Very Light";
                    break;
                case ($precipIntensity <= 0.05):
                    $precipDisplay = "Light";
                    break;
                case ($precipIntensity <= 0.1):
                    $precipDisplay = "Moderate";
                    break;
                case ($precipIntensity > 0.1):
                    $precipDisplay = "Heavy";
                    break;
                default:
                    $precipDisplay = "None";
            }
            echo '<div id="day_forecast_table" style="width:100%">';
            echo '<table align="right" style="margin:0px 40px 20px 0px;">';
            echo '<tr><th style="padding-right:2px;" align="right">Precipitation: </th>';
            echo '<th style="text-align:left;font-size:25px;">' . $precipDisplay . "</th></tr>";
            $precipProbDisplay = round($daily_forecast_data["currently"]["precipProbability"] * 100);
            echo '<tr><th style="padding-right:2px;" align="right">Chance of Rain: </th>';
            echo '<th style="text-align:left;font-size:25px;">' . $precipProbDisplay . '<span style="font-size:15px"> %</span></th></tr>';
            echo '<tr><th style="padding-right:2px;" align="right">Wind Speed: </th>';
            echo '<th style="text-align:left;font-size:25px;">' . $daily_forecast_data["currently"]["windSpeed"] . '<span style="font-size:15px"> mph</span></th></tr>';
            $humidity = round($daily_forecast_data["currently"]["humidity"] * 100);
            echo '<tr><th style="padding-right:2px;" align="right">Humidity: </th>';
            echo '<th style="text-align:left;font-size:25px;">' . $humidity . '<span style="font-size:15px"> %</span></th></tr>';
            echo '<tr><th style="padding-right:2px;" align="right">Visibility: </th>';
            echo '<th style="text-align:left;font-size:25px;">' . $daily_forecast_data["currently"]["visibility"] . '<span style="font-size:15px"> mi</span></th></tr>';
            date_default_timezone_set($daily_forecast_data["timezone"]);
            
            $sunrise = $daily_forecast_data["daily"]["data"][0]["sunriseTime"];
            // alert($sunrise);
            // $sunriseDt = new DateTime("@$sunrise");
            
            $sunriseDt = intval(date('h', $sunrise));
            // alert($sunriseDt->format('Y-m-d H:i:s'));
            // $sunriseTm = $sunriseDt->format('h A');
            $sunset = $daily_forecast_data["daily"]["data"][0]["sunsetTime"];
            // alert($sunset);
            // $sunsetDt = new DateTime("@$sunset");
            $sunsetDt = intval(date('h', $sunset));
            // alert($sunsetDt->format('Y-m-d H:i:s'));
            // $sunsetTm = $sunsetDt->format('h A');
            
            // echo '<p><span style="font-size:15px;float:left;display:inline-block;text-align:right;">Sunrise / Sunset: </span>';
            // echo '<span style="font-size:20px;">' . $sunriseDt . " / " . $sunsetDt . " </span></p><br>";
            echo '<tr><th style="padding-right:2px;" align="right">Sunrise / Sunset: </th>';
            echo '<th style="text-align:left;font-size:25px;">' . $sunriseDt . '<span style="font-size:15px"> AM/ </span>' . $sunsetDt . '<span style="font-size:15px"> PM</span></th></tr>';
            echo "</table></div>";
            echo "<br><br>";
            echo "</div>";
            echo "<br><br>";

            echo "<h1>Day's Hourly Weather</h1>";
            
            // echo '<div id="day_forecast_chart">';
            $upArrow = "https://cdn0.iconfinder.com/data/icons/navigation-set-arrows-part-one/32/ExpandLess-512.png";
            $downArrow = "https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png";
            echo '<img id="upDownArrow" onclick="changeArrow();" src="' . $downArrow . '" style="width:35px; height:35px;text-aling:center"/>';
            echo '<div id="chart" style="width:600px;"></div>';
        }
    ?>
<script type="text/javascript">
    function sendToPHP(arg){
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET","kshar19-forecast.php?" + arg, false);
        xmlhttp.send();
        return xmlhttp.response;
    }
    function checkRefresh(){
        // currentLocation();
        var currloc_checkbox = document.getElementById("currloc");
        currentLocation(currloc_checkbox);
    }
function validateAndSubmit(){
    var currloc_checkbox = document.getElementById("currloc");
    if (currloc_checkbox.checked == false) {
        var street = document.getElementById("street");
        var city = document.getElementById("city");
        var state = document.getElementById("state");
        if ((street.value.length < 1)
            || (city.value.length < 1)
            || (state.value.length < 1)){
            document.getElementById("forecast").innerHTML = "Please check the input address";
            document.getElementById("forecast").style.border = "2px solid grey";
            document.getElementById("forecast").style.padding = "0px 100px";
            return;
        }
        var submit =  document.getElementById("submit");
        html_text = sendToPHP("street=" + street.value + "&city=" + city.value + "&state=" + state.value + "&submit=" + submit.value);
    } else {
        
        jsonLatLong = currentLatLong();
        html_text = sendToPHP("currloc=" + currloc_checkbox.value + "&currcity=" + jsonLatLong.city + "&lat=" + jsonLatLong.lat + "&long=" + jsonLatLong.lon);
        
    }
    // currentLocation();
    document.write(html_text);
    document.close();
    // currentLocation();
    currentLocation(currloc_checkbox);
}

function clearContent(){
    var street = document.getElementById("street");
    var city = document.getElementById("city");
    var state = document.getElementById("state");
    var checkbox = document.getElementById("currloc");
    street.value = "";
    city.value = "";
    state.value = "";
    currloc.checked = false;
    street.disabled = false;
    city.disabled = false;
    state.disabled = false;
    document.getElementById("forecast").innerHTML = "";
    document.getElementById("forecast").style.border = "0px";

}

function loadDailyForecast(element) {
    var currloc_checkbox = document.getElementById("currloc");
    var index = 0;
    index = element.closest('tr').rowIndex;
    index -= 1;
    var jsonobj = <?php echo json_encode($forecast_data); ?>;
    var lat = jsonobj.latitude;
    var long = jsonobj.longitude;
    var time = jsonobj.daily.data[index].time;
   
    var date = new Date(time * 1000);
    var currloc_checkbox = document.getElementById('currloc');
    if (currloc_checkbox.checked == true){
        html_text = sendToPHP("lat=" + lat + "&long=" + long + "&time=" + time + "&currloc=" + currloc_checkbox.value);
    } else {
        html_text = sendToPHP("street=" + street.value + "&city=" + city.value + "&state=" + state.value + "&lat=" + lat + "&long=" + long + "&time=" + time);
    }
    document.write(html_text);
    document.close();
    currentLocation(currloc_checkbox);
}
function changeArrow(){
    upArrow = "https://cdn0.iconfinder.com/data/icons/navigation-set-arrows-part-one/32/ExpandLess-512.png";
    downArrow = "https://cdn4.iconfinder.com/data/icons/geosm-e-commerce/18/point-down-512.png";
    if (document.getElementById('upDownArrow').src == downArrow){
        document.getElementById('upDownArrow').src = upArrow;
        loadChart();
    } else if (document.getElementById('upDownArrow').src == upArrow){
        document.getElementById('upDownArrow').src = downArrow;
        document.getElementById('chart').innerHTML = "";
    }
}

function loadChart(){
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    var jsonobj = <?php echo json_encode($daily_forecast_data); ?>;
    var hourlyList = jsonobj.hourly.data;
    var tempArray = [['Time', 'T']];

    for(i=0; i<hourlyList.length; i++){
        hourlyNode = hourlyList[i];
        tempArray.push([i, hourlyNode["temperature"]]);
    }
        
    function drawChart() {
        var data = google.visualization.arrayToDataTable(tempArray);

        var options = {
            hAxis:{
                title: 'Time'
            },
            vAxis:{
                title: 'Temperature',
                textPosition: 'none'
            },
            colors: ['#097138']
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart'));

        chart.draw(data, options);
      }
}
function currLocCheckBox(){
    var currloc_checkbox = document.getElementById("currloc");
    currentLocation(currloc_checkbox)
}
function currentLocation(currloc_checkbox){
    // var currloc_checkbox = document.getElementById("currloc");
    var street = document.getElementById("street");
    var city = document.getElementById("city");
    var state = document.getElementById("state");
    if(currloc_checkbox.checked == true){
        currloc_checkbox.checked == true
        street.value = "";
        city.value = "";
        state.value = "";
        street.disabled = true;
        city.disabled = true;
        state.disabled = true;
    }else{
        street.disabled = false;
        city.disabled = false;
        state.disabled = false;
    }
}
function currentLatLong(){
    var endpoint = 'http://ip-api.com/json/?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query';

    var xhr = new XMLHttpRequest();
    xhr.open('GET', endpoint, false);
    xhr.send();
    jsonObj= JSON.parse(xhr.responseText);
    return jsonObj;
}

</script>
</div></body></html>
