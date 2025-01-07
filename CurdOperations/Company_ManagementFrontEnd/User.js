const BASE_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', function () {
    fetchRoles();

    // Attach event listener to the form
    document.getElementById('userForm').addEventListener('submit', function (event) {
        event.preventDefault();
        clearErrors();
        const isValid = validateForm();
        if (!isValid) return;
        addUser();
    });
});

function fetchRoles() {
    fetch(BASE_URL + '/api/role/allRoles', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch roles.');
            }
            return response.json();
        })
        .then(roles => {
            const roleDropdown = document.getElementById('role');
            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id; // Set role ID as the value
                option.textContent = role.name; // Set role name as the display text
                roleDropdown.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching roles:', error);
            document.getElementById('message').innerText = 'Unable to load roles.';
        });
}

function validateForm() {
    let isValid = true;

    const firstName = document.getElementById('firstName').value.trim();
    if (!firstName) {
        showError('firstNameError', 'First name is required');
        isValid = false;
    }

    const lastName = document.getElementById('lastName').value.trim();
    if (!lastName) {
        showError('lastNameError', 'Last name is required');
        isValid = false;
    }

    const email = document.getElementById('email').value.trim();
    if (!email) {
        showError('emailError', 'Email cannot be NULL');
        isValid = false;
    } else if (!validateEmail(email)) {
        showError('emailError', 'Please enter a valid email Id');
        isValid = false;
    } else if (email.length > 25) {
        showError('emailError', 'Email cannot exceed 25 characters');
        isValid = false;
    }

    const password = document.getElementById('password').value.trim();
    if (!password) {
        showError('passwordError', 'Password is required');
        isValid = false;
    }

    const roleId = document.getElementById('role').value.trim();
    if (!roleId) {
        showError('roleError', 'Role selection is required');
        isValid = false;
    }

    return isValid;
}

function showError(elementId, message) {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.display = 'block';
    }
}

function clearErrors() {
    const errors = document.querySelectorAll('.error');
    errors.forEach(error => {
        error.textContent = '';
        error.style.display = 'none';
    });
}

function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function addUser() {
    const userData = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        roleId: parseInt(document.getElementById('role').value),
        is_active: true,
    };

    fetch(BASE_URL + '/api/user/saveUser', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData),
    })
        .then(response => response.text())
        .then(data => {
			document.getElementById('message').innerText = data; 
			       document.getElementById('message').style.display = 'block';
			      
            setTimeout(() => {
                document.getElementById('message').style.display = 'none';
            }, 2000);
            clearForm();
        })
        .catch(error => console.error('Error:', error));
}

function clearForm() {
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('email').value = '';
    document.getElementById('password').value = '';
    document.getElementById('role').value = '';
}
