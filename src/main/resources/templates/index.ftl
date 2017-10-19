<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/index.js"></script>


<div style="text-align: center">
    <label>
        <select name="cityId" id="city_id">
        <#list cities as city>
            <option value="${city.id}">${city.name}</option>
        </#list>
        </select>
    </label>
    <button id="get_weather_btn">Get weather</button>
    <h1 id="weather_temperature" style="">

    </h1>
</div>