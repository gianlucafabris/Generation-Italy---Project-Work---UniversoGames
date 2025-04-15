class Utente {
	constructor(id, nome, cognome, username, password) {
    this.id = id;
    this.nome = nome;
    this.cognome = cognome;
    this.username = username;
    this.password = password;
  }
}
let utenteSessione = new Utente(0, '', '', '', '');
Object.cast = function cast(rawObj, constructor) {
	var obj = new constructor();
	for(var i in rawObj){
		obj[i] = rawObj[i];
	}
	return obj;
}
function doLogged() {
	$.get('logged', function(response) {
		let u = Object.cast(response, Utente);
		utenteSessione = new Utente(u.id, u.nome, u.cognome, u.username, u.password);
	});
}
doLogged();
