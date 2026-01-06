document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form[action="/addCar"]');
    
    if (form) {
        form.addEventListener('submit', function(e) {
            const make = document.querySelector('input[name="make"]').value.trim();
            const model = document.querySelector('input[name="model"]').value.trim();
            const year = document.querySelector('input[name="year"]').value;
            
            if (!make || !model) {
                e.preventDefault();
                alert('Please fill in all fields');
                return false;
            }
            
            const currentYear = new Date().getFullYear();
            if (year < 1900 || year > currentYear + 1) {
                e.preventDefault();
                alert('Please enter a valid year between 1900 and ' + (currentYear + 1));
                return false;
            }
            
            return true;
        });
        
        const yearInput = document.querySelector('input[name="year"]');
        if (yearInput) {
            yearInput.addEventListener('input', function() {
                this.value = this.value.replace(/[^0-9]/g, '');
            });
        }
    }
    
    const inputs = document.querySelectorAll('input[type="text"], input[type="number"]');
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.style.borderColor = '#c2185b';
            this.style.outline = 'none';
        });
        
        input.addEventListener('blur', function() {
            this.style.borderColor = '#ddd';
        });
    });
});
