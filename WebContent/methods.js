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
function getInput(){
	var helm = document.getElementById('input').value;
	if(helm == ""){
		var file = document.getElementById('upl');
		if(file.value == ''){
			throw new Error("");
		}
		else{
		var reader = new FileReader();
		reader.onload = function(event) {
			input = reader.result;
			
			
		}
		reader.onerror = function(event) {
			console.error("File could not be read! Code " + event.target.error.code);
			
		};
	 return reader.readAsText(file.files[0]);
	
		}
	}
	return helm;
}

function validate() {
	clean();
	try{
    var helm = getInput;

    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Validation',inputdata).done(function(data){
    		$('ItemPreview').remove();
    		$("#output").text(data.Validation);
    	}).fail(function(j){
    		alert(j.responseText);
    	});
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
   
}

function getJSON() {
	clean();
	try{
    var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
	$.post(baseUrl + 'Conversion/JSON',inputdata).done(function(data){
		$("#output").text(data.JSON);
	}).fail(function(j){
		alert(j.responseText);
	});
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function canonical() {
	clean();
	try{
    var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Conversion/Canonical',inputdata).done(function(data){
		$("#output").text(data.CanonicalHELM);
	}).fail(function(j){
		alert(j.responseText);
	}); 
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}
 
function standard() {
	clean();
	try{
    var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Conversion/Standard',inputdata).done(function(data){
		$("#output").text(data.StandardHELM);
	}).fail(function(j){
		alert(j.responseText);
	}); 
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function imageGeneration(){
	try{
	clean();
	var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Image/HELM',inputdata).done(function(data){
    	$("#output").text('');
    	$("#outputcontainer").append('<img id="ItemPreview" src="" />');
    	document.getElementById("ItemPreview").src = data;
	}).fail(function(j){
		alert(j.responseText);
	}); 
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function calculateMolecularWeight(){
	clean();
	try{
	var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Calculation/MolecularWeight',inputdata).done(function(data){
    	$("#output").text(data.MolecularWeight);
	}).fail(function(j){
		alert(j.responseText);
	});
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function calculateMolecularFormula(){
	try{
	clean();
	var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Calculation/MolecularFormula',inputdata).done(function(data){
    	$("#output").text(data.MolecularFormula);
	}).fail(function(j){
		alert(j.responseText);
	}); 
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function calculateExtinctionCoefficient(){
	clean();
	try{
	var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Calculation/ExtinctionCoefficient',inputdata).done(function(data){
    	$("#output").text(data.ExtinctionCoefficient);
	}).fail(function(j){
		alert(j.responseText);
	}); 
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function getFasta(){
	clean();
	try{
	var helm = getInput;
    var inputdata = {HELMNotation: helm};
    var baseUrl = './service/';
    $.post(baseUrl + 'Fasta/Produce',inputdata).done(function(data){
    	$("#outputcontainer").append('<p id=FASTA>' + data.FastaFile.replace('\n', '<br>') + '</p>');
	}).fail(function(j){
		alert(j.responseText);
	}); 
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function getNaturalAnalogRNA(){
	clean();
	try{
		var helm = getInput;
		var inputdata = {HELMNotation: helm};
		var baseUrl = './service/';
		$.post(baseUrl + 'Fasta/Convert/RNA',inputdata).done(function(data){
			$("#output").text(data.Sequence);
		}).fail(function(j){
			alert(j.responseText);
		}); 
	
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}
function getNaturalAnalogPEPTIDE(){
	clean();
	try{
		var helm = getInput;
		var inputdata = {HELMNotation: helm};
		var baseUrl = './service/';
		$.post(baseUrl + 'Fasta/Convert/PEPTIDE',inputdata).done(function(data){
			console.log(data);
			$("#output").text(data.Sequence);
		}).fail(function(j){
			alert(j.responseText);
		}); 
	}
	catch(e){
		alert("Please insert HELM Notation or upload a file");
	}
}

function clean(){
	$("#output").text('');
	$("#FASTA").remove();
	$("#ItemPreview").remove();
}

function resetAll(){
	clean();
	document.getElementById("input").value = '';
	document.getElementById('upl').value = '';
}

window.onload = function(){
	document.getElementById("Validate").onclick = validate;
	document.getElementById("Json").onclick = getJSON;
	document.getElementById("canonical").onclick = canonical;
	document.getElementById("standard").onclick = standard;
	document.getElementById("image").onclick = imageGeneration;
	document.getElementById("MW").onclick = calculateMolecularWeight;
	document.getElementById("MF").onclick = calculateMolecularFormula;
	document.getElementById("EC").onclick = calculateExtinctionCoefficient;
	document.getElementById("Fasta").onclick = getFasta;
	document.getElementById("naturalSequenceRNA").onclick = getNaturalAnalogRNA;
	document.getElementById("naturalSequencePEPTIDE").onclick = getNaturalAnalogPEPTIDE;
	document.getElementById("reset").onclick = resetAll;
}



