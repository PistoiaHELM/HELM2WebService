/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *****************************************************************************
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