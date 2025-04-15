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
    return `<tr>
    <td>${this.id}</td>
    <td>${this.titoloVideogioco}</td>
    <td>${this.data}</td>
    <td>${this.recensione}</td>
    <td>${this.punteggio}</td>
      <td>
        <button class="modificarecensione btn btn-primary btn-block">modifica</button><br>
        <button class="eliminarecensione btn btn-primary btn-block">elimina</button>
      </td>
    </tr>`;
  }
}
jQuery(function($) {
	const id = $('input[name="idrecensione"]');
	const titolovideogioco = $('input[name="titolovideogioco"]');
	const data = $('input[name="datarecensione"]');
	const recensione = $('textarea[name="recensione"]');
	const punteggio = $('select[name="punteggio"]');
	$('#aggiungirecensione').on('click', function() {
		let r = new Recensione(id.val(), titolovideogioco.val(), data.val(), recensione.val(), punteggio.val(), utenteSessione);
		if ($(this).text() == 'Aggiungi') {
			doPost(r);
		} else {
			doPut(r);
		}
	});
	$('#outputrecensioni').on('click', '.modificarecensione', function() {
		doGet($($(this).parent().parent().children()[0]).text());
	});
	$('#outputrecensioni').on('click', '.eliminarecensione', function() {
		doDelete($($(this).parent().parent().children()[0]).text());
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
  		$.get(`recensioni/utente/${utenteSessione.id}`, function(response) {
  			for (const r of response) {
  				$('#outputrecensioni').append(Object.cast(r, Recensione).render());
  			}
				$('.recensioni tr td:nth-child(2)').collapser({
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
				$('.recensioni tr td:nth-child(4)').collapser({
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
  	} else {
  		$.get(`recensioni/${index}`, function(response) {
  			let r = Object.cast(response, Recensione);
  			id.val(r.id);
  			titolovideogioco.val(r.titoloVideogioco).focus();
  			data.val(r.data);
  			recensione.val(r.recensione);
  			punteggio.val(r.punteggio);
  			data.val(r.data);
  			$('#aggiungirecensione').text('Modifica');
  		});
  	}
  }
	function doPost(r) {
  	$.ajax({
  		url: 'recensioni',
  		type: 'POST',
  		data: JSON.stringify(r),
  		headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
  		success: function(response) {
  			if (response.msg == 'OK') {
  				renderTable();
  				titolovideogioco.val('').focus();
  				data.val('');
  				recensione.val('');
  				punteggio.val(1);
  			} else {
  				alert(response.msg);
  			}
  		}
  	});
  }
	function doPut(r) {
  	$.ajax({
  		url: 'recensioni',
  		type: 'PUT',
  		data: JSON.stringify(r),
  		headers: {
  			'Accept': 'application/json',
  			'Content-Type': 'application/json'
  		},
  		success: function(response) {
  			if (response.msg == 'OK') {
  				renderTable();
  				$('#aggiungirecensione').text('Aggiungi');
  				id.val(-1);
  				titolovideogioco.val('').focus();
  				data.val('');
  				recensione.val('');
  				punteggio.val(1);
  			} else {
  				alert(response.msg);
  			}
  		}
  	});
  }
	function doDelete(index) {
  	$.ajax({
  		url: `recensioni/${index}`,
  		type: 'DELETE',
  		headers: {
  			'Accept': 'application/json',
  			'Content-Type': 'application/json'
  		},
  		success: function(response) {
  			if (response.msg == 'OK') {
  				renderTable();
  			} else {
  				alert(response.msg);
  			}
  		}
  	});
  }
	function renderTable(){
  	$('#outputrecensioni').text('');
    if (utenteSessione.id == 0 && utenteSessione.nome == '' && utenteSessione.cognome == '' && utenteSessione.username == '' && utenteSessione.password == '') {
			$('body').after('<a href="gestione.html"></a>');
			$('body+a')[0].click();
		}
  	doGet();
  }
	renderTable();
	id.val(-1);
});
