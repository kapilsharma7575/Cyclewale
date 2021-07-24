map.on('style.load', function() {
    map.on('click', function(e) {
        let coordinates = e.lngLat;
        document.querySelector('#latitude').value = coordinates["lat"];
        document.querySelector('#longitude').value = coordinates["lng"];
    });
});