/* Confirguração da autenticação após submissão (submit) do form*/
$('#form-contato').submit(function () {
    var nome = $('#nome');
    var email = $('#email');
    var confEmail = $('#confirmacao-email');
    var senha = $('#senha');
    var confSenha = $('#confirmacao-senha');
    var alertaErro = $('.alert');
    var campoErro = $('#campo-erro');

    alertaErro.addClass('d-none'); // Remove o alerta de erro sempre que clicar em submit 
    $('.is-invalid').removeClass('is-invalid');

    //Valida o campo nome
    if (nome.val() == '') {
        alertaErro.removeClass('d-none');
        campoErro.html('nome'); // Nome do campo que não foi preenchido 
        nome.focus();
        nome.addClass('is-invalid');
        return false
    }

    //Valida o campo email
    if (email.val() == '') {
        alertaErro.removeClass('d-none');
        campoErro.html('email'); // Nome do campo que não foi preenchido 
        email.focus();
        email.addClass('is-invalid');
        return false
    }

    //Valida o campo emai e confirma email
    if (confEmail.val() == '') {
        alertaErro.removeClass('d-none');
        campoErro.html('de confirmação do email'); // Nome do campo que não foi preenchido 
        confEmail.focus();
        confEmail.addClass('is-invalid');
        return false
    } else if (confEmail.val() != email.val()) { // Confrimando se o email esta igual ao email confirmado
        alertaErro.removeClass('d-none');
        campoErro.html('de confirmação do email corretamente'); // Nome do campo que não foi preenchido corretamente
        confEmail.focus();
        confEmail.addClass('is-invalid');
        return false
    }

    //Valida o campo senha e confirma senha
    if (senha.val() == '') {
        alertaErro.removeClass('d-none');
        campoErro.html('senha'); // Nome do campo que não foi preenchido 
        senha.focus();
        senha.addClass('is-invalid');
        return false
    } else if (confSenha.val() != senha.val()) { // Confrimando se o email esta igual ao email confirmado
        alertaErro.removeClass('d-none');
        campoErro.html('de confirmação da senha corretamente'); // Nome do campo que não foi preenchido corretamente
        confSenha.focus();
        confSenha.addClass('is-invalid');
        return false
    }

    return true;
});