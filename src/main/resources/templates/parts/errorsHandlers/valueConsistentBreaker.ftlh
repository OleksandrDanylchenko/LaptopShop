<#macro valueBreaker inputName>
    <script>
        function validateForm() {
            const ${inputName} = document.querySelector('[name = "${inputName}"]');
            if (!${inputName}.value.match(/^\d*.*[a-zа-яA-ZА-Я]+.*/)) {
                ${inputName}.value = "";
                return false;
            }
        }
        ${inputName}.focus();
    </script>
</#macro>