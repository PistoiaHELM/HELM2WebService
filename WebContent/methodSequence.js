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