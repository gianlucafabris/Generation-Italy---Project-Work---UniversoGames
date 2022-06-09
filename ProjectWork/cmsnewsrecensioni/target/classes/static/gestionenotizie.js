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
        return `<tr>
              <td>${this.id}</td>
              <td>${this.titolo}</td>
              <td>${this.categoria}</td>
              <td>${this.contenuto}</td>
              <td>${this.data}</td>
              <td><button class="btnModnotizia">modifica</button></td>
              <td><button class="btnDelnotizia">elimina</button></td>
          </tr>`;
    }
}
jQuery(function($) {
    const id = $('input[name="idnotizia"]');
    const titolo = $('input[name="titolo"]');
    const categoria = $('input[name="categoria"]');
    const contenuto = $('input[name="contenuto"]');
    const data = $('input[name="datanotizia"]');
    $('#addnotizia').on('click', function() {
        let n = new Notizia(id.val(), titolo.val(), categoria.val(), contenuto.val(), data.val(), utenteSessione);
        if ($(this).text() == 'Aggiungi') {
            doPost(n);
        } else {
            doPut(n);
        }
    });
    $('#outputnotizie').on('click', '.btnModnotizia', function() {
        doGet($($(this).parent().parent().children()[0]).text());
    });
    $('#outputnotizie').on('click', '.btnDelnotizia', function() {
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
			$.get(`notizie/utente/${utenteSessione.id}`, function(response) {
				for (const n of response) {
					$('#outputnotizie').append(Object.cast(n, Notizia).render());
				}
			});
		} else {
			$.get(`notizie/${index}`, function(response) {
				let n = Object.cast(response, Notizia);
				id.val(n.id);
				titolo.val(n.titolo).focus();
				categoria.val(n.categoria);
				contenuto.val(n.contenuto);
				data.val(n.data);
				$('#addnotizia').text('Modifica');
			});
		}
	}
    function doPost(n) {
		$.ajax({
			url: 'notizie',
			type: 'POST',
			data: JSON.stringify(n),
			headers: {
		        'Accept': 'application/json',
		        'Content-Type': 'application/json'
		    },
			success: function(response) {
				if (response.msg == 'OK') {
					renderTable();
					titolo.val('').focus();
					categoria.val('');
					contenuto.val('');
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
    function doPut(n) {
		$.ajax({
			url: 'notizie',
			type: 'PUT',
			data: JSON.stringify(n),
			headers: {
		        'Accept': 'application/json',
		        'Content-Type': 'application/json'
		    },
			success: function(response) {
				if (response.msg == 'OK') {
					renderTable();
					$('#addnotizia').text('Aggiungi');
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
			url: `notizie/${index}`,
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
		$('#outputnotizie').text('');
		doGet();
	}
    renderTable();
    id.val(-1);
});
