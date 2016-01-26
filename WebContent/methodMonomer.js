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
function generateImageMonomer(){
	clean();
	var monomer = document.getElementById('MonomerID').value;
	var polymer = document.getElementById('PolymerType').value;
	var baseUrl = './service/';
	console.log("Input: " + $("#type").val());
	var inputdata = {monomerId : monomer , polymerType : polymer, showRgroups : $("#type").val()};
	console.log("inputdata:  " + inputdata.showRgroups);
	$.post(baseUrl + 'Image/Monomer', inputdata).done(function(data){
		$("#ItemPreview").remove();
		$("#outputcontainer").append('<img id="ItemPreview" src="" />');
    	document.getElementById("ItemPreview").src = data;
	}).fail(function(j){
		alert('Incorrect Input');
	});
}




function clean(){
	$("#ItemPreview").remove();
}


function resetAll(){
	document.getElementById('MonomerID').value = '';
	document.getElementById('PolymerType').value = '';
	clean();
}

window.onload = function(){
	document.getElementById("monomerImage").onclick = generateImageMonomer;
	document.getElementById("reset").onclick= resetAll;
}