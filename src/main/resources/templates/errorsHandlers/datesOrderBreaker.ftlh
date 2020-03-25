<#macro datesBreaker dateStartId dateEndId>
    <script>
        function validateDates() {
            var dateStart = document.getElementById("${dateStartId}");
            var dateEnd = document.getElementById("${dateEndId}");
            if (dateStart.value > dateEnd.value)
                document.getElementById("${dateEndId}").value = "";
        }
    </script>
</#macro>