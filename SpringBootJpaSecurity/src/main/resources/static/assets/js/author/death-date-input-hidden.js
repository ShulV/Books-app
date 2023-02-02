let authorDeathCheckbox = document.getElementById('author-death-checkbox');
let authorDeathBlock = document.getElementById('author-death-block');
let deathDateInput = document.getElementById('deathDate');
authorDeathCheckbox.addEventListener('change', (e) => {

    if (!authorDeathCheckbox.checked) {
        authorDeathBlock.style.display = "none";//не показываем блок выбора даты смерти
        deathDateInput.value = "";//очищаем дату смерти
    } else {
        authorDeathBlock.style.display = "block";//показываем блок выбора даты смерти
    }
})