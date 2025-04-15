class Utente {
	constructor(id, nome, cognome, username, password) {
    this.id = id;
    this.nome = nome;
    this.cognome = cognome;
    this.username = username;
    this.password = password;
  }
}
class Immagine {
	constructor(id, nome, ruolo) {
    this.id = id;
    this.nome = nome;
		this.ruolo = ruolo;
  }
}
class Notizia {
  constructor(id, titolo, categoria, contenuto, data, autore, copertina, carosello) {
    this.id = id;
    this.titolo = titolo;
    this.categoria = categoria;
    this.contenuto = contenuto;
    this.data = data;
    this.autore = autore;
    this.copertina = copertina;
    this.carosello = carosello;
  }
  render() {
    return `<div class="card">
			<img src="images/${this.copertina.nome}" alt="">
			<div class="con-text">
				<h2>${this.titolo}</h2>
				<p>${this.categoria} <a href="#" class="notiziaTitolo" data-id="${this.id}"><button>Mostra di pi&ugrave;</button></a></p>
			</div>
		</div>`;
  }
}
jQuery(function($) {
  $('#output').on('click', '.notiziaTitolo', function() {
    doGet($(this).attr('data-id'));
  });
  $('#notizia').on('click', '#back', function() {
    renderOutput();
  });
	Object.cast = function cast(rawObj, constructor) {
    var obj = new constructor();
    for(var i in rawObj){
      obj[i] = rawObj[i];
    }
    return obj;
	}
	function doGet(index) {
		if(index == undefined) {
			$.get('notizie', function(response) {
				for (const n of response) {
					$('#output').append(Object.cast(n, Notizia).render());
				}
        $('#containeroutput').show();
        $('#notizia').hide();
			});
		} else {
			$.get(`notizie/${index}`, function(response) {
				let n = Object.cast(response, Notizia)
				let notizia =`<a href="index.html" id="back">Torna indietro</a>
        <h1>${n.titolo}</h1>
        <img src="images/${n.copertina.nome}" alt="">
        <p id="contenutonotizia">${n.contenuto}</p>`;
				notizia += `<div class="wrappercarosel">
					<section class="carousel" aria-label="Gallery">
	          <ol class="carousel__viewport">`;
				for (let i = 0; i < n.carosello.length; i++) {
					notizia += `<li id="carousel__slide${i}" tabindex="${i}" class="carousel__slide">
		              <img src="images/${n.carosello[i].nome}">
		              <div class="carousel__snapper"></div>`;
					if (i == 0) {
						notizia += `<a href="#carousel__slide${n.carosello.length - 1}" class="carousel__prev">Go to last slide</a>
		                	<a href="#carousel__slide${i + 1}" class="carousel__next">Go to next slide</a>`;
					} else if (i == n.carosello.length - 1) {
						notizia += `<a href="#carousel__slide${i - 1}" class="carousel__prev">Go to previous slide</a>
		                	<a href="#carousel__slide${0}" class="carousel__next">Go to first slide</a>`;
					} else {
						notizia += `<a href="#carousel__slide${i - 1}" class="carousel__prev">Go to previous slide</a>
		                	<a href="#carousel__slide${i + 1}" class="carousel__next">Go to next slide</a>`;
					}
					notizia += `</li>`;
				}
				notizia += `</ol>
	          <aside class="carousel__navigation">
	            <ol class="carousel__navigation-list">`;
				for (let i = 0; i < n.carosello.length; i++) {
					notizia += `<li class="carousel__navigation-item">
	                <a href="#carousel__slide${i}" class="carousel__navigation-button">Go to slide ${i}</a>
	              </li>`;
				}
				notizia += `</ol>
	          </aside>
	        </section>
				</div>`;
				notizia += `<p class="alignright"><span>${n.categoria}</span> - <span>${n.data}</span> - <span>${n.autore.nome} ${n.autore.cognome}</span></p>`;
        $('#containeroutput').hide();
        $('#notizia').html(notizia).show();
				$('#contenutonotizia').collapser({
			    mode: 'lines',
			    truncate: 5,
			    ellipsis: '...',
			    controlBtn: '',

			    showText: 'Mostra di pi&ugrave;',
			    hideText: 'Nacondi testo',
			    showClass: 'show-class',
			    hideClass: 'hide-class',

			    atStart: 'hide',
			    blockTarget: 'next',
			    blockEffect: 'fade',

			    lockHide: false,
			    changeText: false,

			    beforeShow: null,
			    afterShow: null,
			    beforeHide: null,
			    afterHide: null
				});
			});
		}
	}
  function renderOutput(){
		$('#output').text('');
		doGet();
	}
  renderOutput();
});
