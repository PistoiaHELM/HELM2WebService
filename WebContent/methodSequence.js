/**
 * 
 */


function clean(){
	$("#output").text('');
}

function readSequence(){
	clean();
	var sequence = document.getElementById('input').value;
	var baseUrl = './service/Sequence/' + $("#type").val() + '/';
	$.get( baseUrl + sequence).done(function(data){
    	$("#output").text(data.HELMNotation);
	}).fail(function(j){
		if(j.responseText==""){
			alert("Please insert a sequence");
		}
		else{
			alert(j.responseText);
		}
	});
}

function resetAll(){
	clean();
	document.getElementById('input').value = '';
	$("#type").val('PEPTIDE');
}

window.onload = function(){
	document.getElementById("readSequence").onclick = readSequence;
	document.getElementById("reset").onclick = resetAll;

}