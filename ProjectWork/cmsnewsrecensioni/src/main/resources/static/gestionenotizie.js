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
    let notizia = `<tr>
      <td>${this.id}</td>
      <td>${this.titolo}</td>
      <td>${this.categoria}</td>
      <td>${this.contenuto}</td>
      <td>${this.data}</td>
      <td>
        <img src="images/${this.copertina.nome}" alt="">
        <br><br>`;
    for (var immagine of this.carosello) {
      notizia += `<img src="images/${immagine.nome}" alt=""><br>`;
    }
    notizia += `</td>
      <td>
        <button class="modificanotizia">modifica</button><br>
        <button class="eliminanotizia">elimina</button>
      </td>
    </tr>`;
    return notizia;
  }
}
jQuery(function($) {
  const id = $('input[name="idnotizia"]');
  const titolo = $('input[name="titolo"]');
  const categoria = $('input[name="categoria"]');
  const contenuto = $('textarea[name="contenuto"]');
  const data = $('input[name="datanotizia"]');
  const copertina = $('input[name="immaginecopertina"]');
  /*gestione immagini carosello*/
  function nuovaImmagine(i){
    if (isNaN(i)) {
      i = 1;
    }
    $('#aggiungiimmagine').before(`<label for="immaginecarosello${i}" data-indeximg="${i}">immagine carosello ${i}</label> <input type="file" name="immaginecarosello${i}" id="immaginecarosello${i}" data-indeximg="${i}"> <img src="images/[IMG]" alt="" data-indeximg="${i}"> <button id="eliminaimmagine${i}" data-indeximg="${i}">&times;</button><br>`);
  }
  function eliminaImmagine(index) {
    $(`[data-indeximg="${index}"]+br`).each(function(){$(this).remove()});
    $(`[data-indeximg="${index}"]`).each(function(){$(this).remove()});
    riordina();
  }
  function riordina(){
    $('[data-indeximg]').each(function(index){
      if (index % 4 == 0) {
        $(this).attr('for', `immaginecarosello${Math.floor(index / 4) + 1}`).attr('data-indeximg', Math.floor(index / 4) + 1).text(`immagine carosello ${Math.floor(index / 4) + 1}`);
      } else if (index % 4 == 1) {
        $(this).attr('name', `immaginecarosello${Math.floor(index / 4) + 1}`).attr('id', `immaginecarosello${Math.floor(index / 4) + 1}`).attr('data-indeximg', Math.floor(index / 4) + 1);
      } else if (index % 4 == 2) {
        $(this).attr('data-indeximg', Math.floor(index / 4) + 1);
      } else {
        $(this).attr('id', `eliminaimmagine${Math.floor(index / 4) + 1}`).attr('data-indeximg', Math.floor(index / 4) + 1);
      }
    });
  }
  nuovaImmagine(1);
  $('#aggiungiimmagine').on('click', function(){
    nuovaImmagine(parseInt($('#aggiungiimmagine').prev().prev().attr('data-indeximg')) + 1);
  });
  $('.notizie').on('click', '[id^=eliminaimmagine]', function(){
    eliminaImmagine($(this).attr('data-indeximg'));
  });
	copertina.on('change', function(e){
		// let src = 'images/[IMG]';
		let src = URL.createObjectURL(e.target.files[0]);
		$(this).next().attr('src', src);
	});
  $('.notizie').on('change', '[name^=immaginecarosello]', function(e){
		// let src = 'images/[IMG]';
		let src = URL.createObjectURL(e.target.files[0]);
		$(this).next().attr('src', src);
  });
  /*gestione notizia*/
  $('#aggiunginotizia').on('click', function() {
    let n = new Notizia(id.val(), titolo.val(), categoria.val(), contenuto.val(), data.val(), utenteSessione);
    if (copertina.val() != '') {
      n.copertina = new Immagine(-1, copertina.val().split('\\')[copertina.val().split('\\').length - 1], 'copertina');
    } else if (copertina.next().attr('src').split('images/').length == 2) {
    	n.copertina = new Immagine(-1, copertina.next().attr('src').split('images/')[1], 'copertina');
    }
    let index = 0;
    n.carosello = [];
    $('[id^=immaginecarosello]').each(function(){
      if ($(this).val() != '') {
        n.carosello[index] = new Immagine(-1, $(this).val().split('\\')[$(this).val().split('\\').length - 1], 'carosello');
        index++;
      } else if ($(this).next().attr('src').split('images/').length == 2) {
        n.carosello[index] = new Immagine(-1, $(this).next().attr('src').split('images/')[1], 'carosello');
        index++;
	    }
    });
    if ($(this).text() == 'Aggiungi') {
      doPost(n);
    } else {
      doPut(n);
    }
  });
  $('#outputnotizie').on('click', '.modificanotizia', function() {
    doGet($($(this).parent().parent().children()[0]).text());
  });
  $('#outputnotizie').on('click', '.eliminanotizia', function() {
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
				$('.notizie tr td:nth-child(2)').collapser({
			    mode: 'lines',
			    truncate: 5,
			    ellipsis: '...',
			    controlBtn: '',

			    showText: 'Mostra di più',
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
				$('.notizie tr td:nth-child(3)').collapser({
			    mode: 'lines',
			    truncate: 5,
			    ellipsis: '...',
			    controlBtn: '',

			    showText: 'Mostra di più',
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
				$('.notizie tr td:nth-child(4)').collapser({
			    mode: 'lines',
			    truncate: 5,
			    ellipsis: '...',
			    controlBtn: '',

			    showText: 'Mostra di più',
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
			$.get(`notizie/${index}`, function(response) {
				let n = Object.cast(response, Notizia);
				id.val(n.id);
				titolo.val(n.titolo).focus();
				categoria.val(n.categoria);
				contenuto.val(n.contenuto);
				data.val(n.data);
        //copertina.val(n.copertina.nome);
				copertina.next().attr('src', `images/${n.copertina.nome}`)
        $('[id^=eliminaimmagine]').each(function(){
          $(this).click();
        });
        let index = 1;
        for (var immagine of n.carosello) {
          $('#aggiungiimmagine').click();
          //$('#aggiungiimmagine').prev().prev().prev().prev().val(immagine.nome);
          $('#aggiungiimmagine').prev().prev().prev().attr('src', `images/${immagine.nome}`);
          index++;
        }
				$('#aggiunginotizia').text('Modifica');
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
          copertina.val('');
          $('[id^=eliminaimmagine]').each(function(){
            $(this).click();
          });
          $('#aggiungiimmagine').click();
				} else {
					alert(response.msg);
				}
			}
		});
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
					$('#aggiunginotizia').text('Aggiungi');
					id.val(-1);
					titolo.val('').focus();
					categoria.val('');
					contenuto.val('');
					data.val('');
          //copertina.val('');
          $('[id^=eliminaimmagine]').each(function(){
            $(this).click();
          });
          $('#aggiungiimmagine').click();
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
