class Recensione {
    constructor(id, data, recensione, punteggio, autore) {
        this.id = id;
        this.data = data;
        this.recensione = recensione;
        this.punteggio = punteggio;
        this.autore = autore;
    }
    render() {
        return `<tr>
              <td>${this.id}</td>
              <td>${this.data}</td>
              <td>${this.recensione}</td>
              <td>${this.punteggio}</td>
              <td><button class="btnModrecensione">modifica</button></td>
              <td><button class="btnDelrecensione">elimina</button></td>
          </tr>`;
    }
}
jQuery(function($) {
	const id = $('input[name="idrecensione"]');
	const data = $('input[name="datarecensione"]');
	const recensione = $('input[name="recensione"]');
	const punteggio = $('input[name="punteggio"]');
	$('#addrecensione').on('click', function() {
			let r = new Recensione(id.val(), data.val(), recensione.val(), punteggio.val(), utenteSessione);
			if ($(this).text() == 'Aggiungi') {
					doPost(r);
			} else {
					doPut(r);
			}
	});
	$('#outputrecensioni').on('click', '.btnModrecensione', function() {
			doGet($($(this).parent().parent().children()[0]).text());
	});
	$('#outputrecensioni').on('click', '.btnDelrecensione', function() {
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
		});
	} else {
		$.get(`recensioni/${index}`, function(response) {
			let r = Object.cast(response, Recensione);
			id.val(r.id);
			data.val(r.data).focus();
			recensione.val(r.recensione);
			punteggio.val(r.punteggio);
			data.val(r.data);
			$('#addrecensione').text('Modifica');
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
				data.val('').focus();
				recensione.val('');
				punteggio.val('');
				data.val('');
			} else {
				alert(response.msg);
			}
		}
	});
	/*
	$.post('prodotti', JSON.stringify(p), function(response) {
		renderTable();
				nome.val('');
			descrizione.val('');
			prezzo.val('');
			disponibilita.val('');
			nomeCategoria.val('');
			iva.val('');
	});
	*/
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
				$('#addrecensione').text('Aggiungi');
				id.val(-1);
				id.val('');
				titolo.val('').focus();
				categoria.val('');
				contenuto.val('');
				data.val('');
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
	doGet();
}
	renderTable();
	id.val(-1);
});
