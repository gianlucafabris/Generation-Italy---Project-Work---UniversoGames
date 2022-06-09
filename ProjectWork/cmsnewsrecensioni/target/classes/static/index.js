class Utente {
	constructor(id, nome, cognome, username, password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
    }
}
class Notizia {
    constructor(id, titolo, categoria, contenuto, data, autore) {
        this.id = id;
        this.titolo = titolo;
        this.categoria = categoria;
        this.contenuto = contenuto;
        this.data = data;
        this.autore = autore;
    }
    render() {
        return `<div class="card shadow-sm">
            <svg class="bd-placeholder-img card-img-top" width="100%" height="225"
              xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail"
              preserveAspectRatio="xMidYMid slice" focusable="false">
              <title>Placeholder</title>
              <rect width="100%" height="100%" fill="#55595c" /><text x="50%" y="50%" fill="#eceeef"
                dy=".3em">Thumbnail</text>
            </svg>
            <div class="card-body">
              <p class="card-text"><a href="#" class="notiziaTitolo" data-id="${this.id}">${this.titolo}</a></p>
            </div>
          </div>`;
    }
}
jQuery(function($) {
    $('#output').on('click', '.notiziaTitolo', function() {
        doGet($(this).attr('data-id'));
    });
    $('#back').on('click', function() {
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
        $('#output').show();
        $('#notizia').hide();
			});
		} else {
			$.get(`notizie/${index}`, function(response) {
				let n = Object.cast(response, Notizia)
		        $('#titolo').text(n.titolo);
		        $('#categoria').text(n.categoria);
            $('#contenuto').html(n.contenuto);
            $('#data').text(n.data);
            $('#autore').text(n.autore.nome + " " + n.autore.cognome);
            $('#output').hide();
            $('#notizia').show();
			});
		}
	}
  function renderOutput(){
		$('#output').text('');
		doGet();
	}
    renderOutput();
});
