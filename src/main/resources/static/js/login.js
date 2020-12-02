/*<![CDATA[*/
function onLogin(){
	if (auth.estadoAutenticacao){
		alert("Login bem sucedido para o usuário " + auth.usuario.nome)
	} else {
		alert("login falhou.")
	}
	
	sessionStorage.setItem('userId', auth.usuario.codUsuario);
}

function onLogout(){
	sessionStorage.removeItem('userId');
	window.location.href = '/test'
}
/*]]>*/

