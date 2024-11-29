function validate(form) {
  if(form.content == null || form.content.length == 0){
    return false;
  }
  return true;
}
function escapeHtml(str) {
  const element = document.createElement('div');
  if (str) {
    element.innerText = str;
    element.textContent = str;
  }
  return element.innerHTML;
}

const form = document['form-write-board'];
const textArea = document.getElementById("text-area");
document.getElementById("write").addEventListener('click', e=>{
  e.preventDefault();
  form['content'].value = escapeHtml(textArea.innerHTML);
  if(validate(form)){
    form.submit();
  }
})
document.getElementById("text-area").addEventListener("input", function() {
  this.style.height = "auto"; // Reset the height before recalculating
  this.style.height = (this.scrollHeight) + "px"; // Adjust to scrollHeight
});