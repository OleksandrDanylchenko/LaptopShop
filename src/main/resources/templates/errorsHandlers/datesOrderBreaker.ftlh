<#macro datesBreaker dateStartId dateEndId>
    <script>
        function validateDates() {
            let dateStart = document.getElementById("${dateStartId}");
            let dateEnd = document.getElementById("${dateEndId}");
            if (dateStart.value > dateEnd.value)
                document.getElementById("${dateEndId}").value = "";
        }
    </script>
</#macro>