class Utente {
	constructor(id, nome, cognome, username, password) {
    this.id = id;
    this.nome = nome;
    this.cognome = cognome;
    this.username = username;
    this.password = password;
  }
}
class Recensione {
  constructor(id, titoloVideogioco, data, recensione, punteggio, autore) {
    this.id = id;
    this.titoloVideogioco = titoloVideogioco;
    this.data = data;
    this.recensione = recensione;
    this.punteggio = punteggio;
    this.autore = autore;
  }
  render() {
    return `<div class="d-flex text-muted pt-3">
			<svg class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false">
				<title>${this.autore.nome} ${this.autore.cognome}</title>
				<rect width="100%" height="100%" fill="#007bff" /><text x="50%" y="50%" fill="#007bff" dy=".3em"></text>
			</svg>
			<p class="pb-3 mb-0 small lh-sm border-bottom">
					<span class="recensione" data-id="${this.id}">${this.recensione}</span>
				<strong class="d-block text-gray-dark">${this.titoloVideogioco} - ${this.punteggio}/10 - ${this.data} - ${this.autore.nome} ${this.autore.cognome}</strong>
			</p>
		</div>`;
  }
}
jQuery(function($) {
	Object.cast = function cast(rawObj, constructor) {
    var obj = new constructor();
    for(var i in rawObj){
      obj[i] = rawObj[i];
    }
    return obj;
	}
	function doGet() {
    $.get('recensioni', function(response) {
      for (const r of response) {
        $('#output').append(Object.cast(r, Recensione).render());
      }
			$('.recensione').collapser({
				mode: 'words',
				truncate: 50,
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
  function renderOutput(){
		$('#output').text('');
		doGet();
	}
  renderOutput();
});
