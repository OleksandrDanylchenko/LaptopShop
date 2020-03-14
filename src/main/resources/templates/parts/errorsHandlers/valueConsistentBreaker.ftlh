<#macro valueBreaker inputName>
    <script>
        function validateForm() {
            const ${inputName} = document.querySelector('[${inputName} = "${inputName}"]');
            if (!${inputName}.value.match(/^\d*.*[a-zа-яA-ZА-Я]+.*/)) {
                ${inputName}.value = "";
                return false;
            }
        }
        ${inputName}.focus();
    </script>
</#macro>