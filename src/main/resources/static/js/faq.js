const accordion_button = document.querySelectorAll('.container .accordion .accordion-item button');
const all_item = document.querySelectorAll('.container .accordion .accordion-item');
accordion_button.forEach((ele) => {
	ele.addEventListener('click', () => {
		let content = ele.nextElementSibling;
		let active = document.querySelector('.container .accordion .accordion-item.active');
		if (active) {
			if (ele.parentElement.classList.contains('active')) {
				ele.parentElement.classList.remove('active');
				active.lastElementChild.style.height = '0';
			} else {
				active.classList.remove('active');
				active.lastElementChild.style.height = '0';
				ele.parentElement.classList.add('active');
				content.style.height = content.scrollHeight + 'px';
			}
		} else {
			ele.parentElement.classList.add('active');
			content.style.height = content.scrollHeight + 'px';
		}
	});
});
console.log(accordion_button);
