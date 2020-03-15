<#macro emptyBreaker inputName>
    <script>
        const ${inputName} = document.querySelector('[name = "${inputName}"]');
        ${inputName}.onkeyup = function() {
            if (this.value.match(/^[ ]+$/))
                this.value = "";
        };
        ${inputName}.focus();
    </script>
</#macro>