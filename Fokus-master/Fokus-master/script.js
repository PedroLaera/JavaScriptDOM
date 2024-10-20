const html = document.querySelector('html')
const botaoIniciar = document.querySelector('.app__card-primary-butto-icon')
const focoBt = document.querySelector('.app__card-button--foco')
const curtoBt = document.querySelector('.app__card-button--curto')
const longoBt = document.querySelector('.app__card-button--longo')
const banner = document.querySelector('.app__image')
const titulo = document.querySelector('.app__title')
const button = document.querySelectorAll('.app__card-button')
const startPauseBt = document.querySelector('#start-pause')
const musicaFocoInput = document.querySelector('#alternar-musica')
const musica = new Audio ('sons/luna-rise-part-one.mp3')
const audioPlay =  new Audio ('sons/play.wav')
const audioPause =  new Audio ('sons/pause.mp3')
const audioZero = new Audio ('sons/beep.mp3')

let tempoDecorridoEmSegundos = 5
let intervaloId = null


musica.loop = true


musicaFocoInput.addEventListener('change', () => {
    if(musica.paused) {
            musica.play()
        } else {
            musica.pause()
        }
})



focoBt.addEventListener('click', () => {
    alterarContexto('foco')
    focoBt.classList.add('active')
})

curtoBt.addEventListener('click', () => {
    alterarContexto('descanso-curto')
    curtoBt.classList.add('active')
})

longoBt.addEventListener('click', () => {
    alterarContexto('descanso-longo')
    longoBt.classList.add('active')
})

function alterarContexto(contexto) {
    button.forEach(function (contexto){
        contexto.classList.remove('active')
    })
    html.setAttribute('data-contexto', contexto)
    banner.setAttribute('src', `imagens/${contexto}.png`)
    switch (contexto) {
        case "foco":
            titulo.innerHTML = `
            Otimize sua produtividade,<br>
                <strong class="app__title-strong">mergulhe no que importa.</strong>
            `
            break;

        case "descanso-curto":
            titulo.innerHTML = `
            Que tal dar uma respirada? <br>
                <strong class="app__title-strong">Faça uma pausa curta!</strong>
            `
            break;
        case "descanso-longo":
            titulo.innerHTML = `
            Hora de voltar à superfície.
                <strong class="app__title-strong">Faça uma pausa longa.</strong>
            `
            break;
        default:
            break;
    }
}

const contagemRegressiva = () => {
    if(tempoDecorridoEmSegundos <= 0) {
        audioZero.play () =
        alert('tempo finalizado')
        zerar()
        return 
    }
    tempoDecorridoEmSegundos -= 1
    console.log('tempo: ' + tempoDecorridoEmSegundos)
    console.log('Id: ' + intervaloId)
}


startPauseBt.addEventListener('click', iniciar) 

function iniciar() {
    if(intervaloId) {
        audioPause.play()
        zerar()
        return
    }
    audioPause.play();
    intervaloId = setInterval(contagemRegressiva, 1000)
}

function zerar () {
    clearInterval(intervaloId)
    intervaloId = null
}






// const htmls = document.querySelector('html');
// const displayTempo = document.querySelector('#timer');








const duracaoFoco = 1500; 
const duracaoDescansoCurto = 300; 
const duracaoDescansoLongo = 900; 