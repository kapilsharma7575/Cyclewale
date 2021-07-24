domain = "https://cyclewale.herokuapp.com";
// domain = "http://localhost:8080"

journeyData = document.querySelector('#journeyData').querySelectorAll('span');

function timeout(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function showRouteByName()
{
    const originCoords = await axios
        .get(`${domain}/user/getStandCoords/${journeyData[0].textContent}`)
        .then(response => response.data);
    const destinationCoords = await axios
        .get(`${domain}/user/getStandCoords/${journeyData[1].textContent}`)
        .then(response => response.data);

    await timeout(500);
    getRoute(originCoords, destinationCoords);
}

showRouteByName();

document.querySelector('.stopJourney-btn').onclick = async function () {
    this.disabled = true;
    await axios.get(`${domain}/user/stop_journey`);
    window.location.reload();
};