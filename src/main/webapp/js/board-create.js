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
const content = document.getElementById("content");
form.addEventListener('submit', e=>{
  e.preventDefault();
  this.content.value = escapeHtml(content.innerHTML);
  console.log(this.content.value)
  if(validate(this)){
    this.submit();
  }
})
document.getElementById("content").addEventListener("input", function() {
  this.style.height = "auto"; // Reset the height before recalculating
  this.style.height = (this.scrollHeight) + "px"; // Adjust to scrollHeight
});