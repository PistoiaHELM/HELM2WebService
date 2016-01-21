/**
 * 
 */
function clean(){
	$("#output").text('');
}
function readFastaSequence(){
	clean();
	var input;
	var type = $("#type").val(); 
	if(document.getElementById('fasta').value ==  ''){
		var file = document.getElementById('upl');
			if(file.value == ''){
				alert("Please insert Fasta sequence(s) or upload a file");
			}
			else{
			var reader = new FileReader();
			reader.onload = function(event) {
				input = reader.result;
				readFastaSequenceInnerMethod(type,input);
				
				
			}
			reader.onerror = function(event) {
				console.error("File could not be read! Code " + event.target.error.code);
			};
		var j = reader.readAsText(file.files[0]);
		
			}
	}
	
	else{
	input = document.getElementById('fasta').value;
	readFastaSequenceInnerMethod(type,input);

	
	}

}

function readFastaSequenceInnerMethod(type, input){
	var inputdata = {PEPTIDE: input};
	if(type == 'RNA'){
		 inputdata = {RNA: input};
	}
	var baseUrl = './service/';
	$.post(baseUrl + 'Fasta/Read' + '/' + type, inputdata).done(function(data){
    	$("#output").text(data.HELMNotation);
	}).fail(function(j){
		alert(j.responseText);
	});
}

function resetAll(){
	clean();
	document.getElementById("fasta").value = '';
	document.getElementById('upl').value = '';
	$("#type").val('PEPTIDE');
}
window.onload = function(){
	document.getElementById("readFasta").onclick = readFastaSequence;
	document.getElementById("reset").onclick = resetAll;
}