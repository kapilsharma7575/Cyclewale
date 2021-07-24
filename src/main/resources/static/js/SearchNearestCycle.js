userLocation = [];
domain = "https://cyclewale.herokuapp.com"
// domain = "http://localhost:8080"

function getDistance(coords1, coords2) {
    var options = { units: 'meters' };
    return parseFloat(turf.distance(coords1,coords2,options));
}

function sortByDistance(standA,standB)
{
    return getDistance(standA.coords, userLocation) - getDistance(standB.coords, userLocation);
}

function timeout(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function nearestStand() {
    const stands = await axios.get(`${domain}/user/getStands`).then(response => response.data);
    distance = 10000000;
    beginCoords = [];
    stands.sort(sortByDistance);
    logsList = document.querySelector('#searchLogsList');
    for(i=0; i<stands.length; i++)
    {
        log =  document.createElement('li');
        log.textContent = `Looking for cycle at ${stands[i].name}...`;
        logsList.appendChild(log);
        await timeout(500);
        const cycleNo = await axios.get(`${domain}/user/check_availability/${stands[i].name}`).then(response => response.data);
        if(cycleNo != -1){
            log =  document.createElement('li');
            log.textContent = 'Cycle Found!';
            logsList.appendChild(log);
            await timeout(500);
            distance = getDistance(stands[i].coords, userLocation);
            beginCoords = stands[i].coords;
            getRoute(userLocation, beginCoords);

            document.querySelector('.chooseDestination-bottom')
                .style.display = "";

            let optionsList = document.querySelector('.destination-select');

            for( j=0; j<stands.length; j++)
            {
                let option =  document.createElement('option');
                option.textContent = stands[j].name;
                option.setAttribute("value",stands[j].name);
                optionsList.appendChild(option);
            }

            return [stands[i].name,cycleNo];

        }
        else {
            log =  document.createElement('li');
            log.textContent = 'Cycle not Available, searching next nearest Stand...';
            logsList.appendChild(log);
            await timeout(500);
        }
    }
    return ["",-1];
}

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(async function showPosition(position) {
            userLocation = [position.coords.longitude, position.coords.latitude];

            document.querySelector('.askForLocation')
                .style.display = "none";

            document.querySelector('.chooseDestination')
                .style.display = "";

            cycleNoAndStand = await nearestStand();
            if(cycleNoAndStand[1] == -1)
                document.querySelector('.tellNearestStand').
                  textContent = `There is no cycle available at the moment.\r\n असुविधा के लिए खेद है|`;

            else {
                document.querySelector('.tellNearestStand').
                    textContent = `Cycle No. - ${cycleNoAndStand[1]} is available at: ${cycleNoAndStand[0]}`;
                document.querySelector('.distanceToStand').
                    textContent = `Distance = ${distance.toPrecision(5)} metres`;
            }
        });

    } else {

        var askForLocationText = document.querySelector('.askForLocationText')

        askForLocation.textContent = "There was some problem accessing your Location";
        askForLocation.textContent += "Please try Again!";

    }
}

async function showRouteToDestination(){

    const stands = await axios.get(`${domain}/user/getStands`).then(response => response.data);
    var destination = document.querySelector('.destination-select').value;
    for(i=0; i<stands.length; i++)
    {
        if(destination == stands[i].name) {
            getRoute(beginCoords, stands[i].coords);

            document.querySelector('.distanceToDestination')
                .textContent = `The selected stand is at a distance of ${getDistance(stands[i].coords, beginCoords).toPrecision(5)} metres`
        }
    }
}

async function startJourney() {
    var destination = document.querySelector('.destination-select').value;
    let jsonObject = {
        'origin': `${cycleNoAndStand[0]}`,
        'destination': `${destination}`,
        'cycleId': `${cycleNoAndStand[1]}`
    };

    await axios.post(`${domain}/user/start_journey`, jsonObject);
    window.location.reload();
}

document.querySelector('.show-route-btn').onclick = function(){
    showRouteToDestination();
}

document.querySelector('.askForLocation-btn').onclick = function(){
    document.querySelector('.askForLocation-btn').disabled = true;
    getLocation();
}





