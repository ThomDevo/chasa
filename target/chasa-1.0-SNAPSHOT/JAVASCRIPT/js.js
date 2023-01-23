//function for submit form language in header (reload page after select new language).
function submitLanguageForm(idForm){
    let domForm = document.getElementById(idForm);
    if(!domForm){
        console.log('form not found with id ['+idForm+'].');
        return;
    }
    domForm.submit();
}

function validateConnection(){

    let result = true;
    const lifrasNumber = document.forms["connections"]["float-input"];
    const password = document.forms["connections"]["toggle"];

    if (lifrasNumber.value === "" || lifrasNumber.value == null){
        document.getElementById("errLifrasNumberEmptySpan").style.display = "block";
        label.style.border = "1px solid red";
        label.style.boxShadow = "0 0 1px 2px red"
    }else{
        document.getElementById("errLifrasNumberEmptySpan").style.display = "none";
        label.removeAttribute('style');
    }

    return result;

}