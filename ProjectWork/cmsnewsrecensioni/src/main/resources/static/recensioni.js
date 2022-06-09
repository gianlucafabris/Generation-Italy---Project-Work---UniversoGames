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
    constructor(id, data, recensione, punteggio, autore) {
        this.id = id;
        this.data = data;
        this.punteggio = punteggio;
        this.autore = autore;
    }
    render() {
        return `<div class="recensione" data-id="${this.id}">
            <p class="recensione">${this.recensione}</p>
            <p><span class="punteggio">${this.punteggio}</span>/10 - <span class="data">${this.data}</span> - <span class="autore">${this.autore.nome} ${this.autore.cognome}</span></p>
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
    });
	}
  function renderOutput(){
		$('#output').text('');
		doGet();
	}
    renderOutput();
});
