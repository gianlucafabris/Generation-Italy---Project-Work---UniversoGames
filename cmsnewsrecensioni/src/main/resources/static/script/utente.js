class Utente {
	constructor(id, nome, cognome, username, password) {
    this.id = id;
    this.nome = nome;
    this.cognome = cognome;
    this.username = username;
    this.password = password;
  }
}
jQuery(function($) {
	const accedi = $('#tab-1');
	const registrati = $('#tab-2');
	const usernameLogin = $('input[name="usernameLogin"]');
	const passwordLogin = $('input[name="passwordLogin"]');
	const nomeSignin = $('input[name="nomeSignin"]');
	const cognomeSignin = $('input[name="cognomeSignin"]');
	const usernameSignin = $('input[name="usernameSignin"]');
	const passwordSignin = $('input[name="passwordSignin"]');
	$('#loginButton').on('click', function() {
		let u = new Utente(-1, '', '', usernameLogin.val(), passwordLogin.val());
		doLogin(u);
	});
	$('#signinButton').on('click', function() {
		let u = new Utente(-1, nomeSignin.val(), cognomeSignin.val(), usernameSignin.val(), passwordSignin.val());
		doSignin(u);
	});
  Object.cast = function cast(rawObj, constructor) {
		var obj = new constructor();
		for(var i in rawObj){
			obj[i] = rawObj[i];
		}
		return obj;
  }
	function doLogin(u){
		let fd = new FormData();
		fd.append('username', u.username);
		fd.append('password', u.password);
		$.ajax({
			url: 'login',
			type: 'POST',
			data: fd,
			headers: {
				'Accept': 'application/json'
			},
			contentType: false,
			processData: false,
			success: function() {
				//no errore se sbaglia password o username
				$('body').after('<a href="gestione.html"></a>');
				$('body+a')[0].click();
			}
		});
	}
	function doSignin(u){
		let fd = new FormData();
		fd.append('nome', u.nome);
		fd.append('cognome', u.cognome);
		fd.append('username', u.username);
		fd.append('password', u.password);
		$.ajax({
			url: 'signin',
			type: 'POST',
			data: fd,
			headers: {
				'Accept': 'application/json'
			},
			contentType: false,
			processData: false,
			success: function() {
				//no errore se vuoto
				accedi.click();
				usernameLogin.val(usernameSignin.val());
				passwordLogin.val(passwordSignin.val());
				$('#loginButton').click();
			},
			error: function() {
				//errore solo se username già preesente
				accedi.click();
				usernameLogin.val(usernameSignin.val());
			}
		});
	}
	$('#linkRegistrati').on('click', function() {
		registrati.click();
	});
});
