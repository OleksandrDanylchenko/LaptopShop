<#import "parts/htmlPad.ftlh" as pad>

<@pad.page "Alex Laptop Shop">
    <div>
        <h1 class="landingText">Доброго дня!</h1>
        <p class="landingText">Нижче представлено перелік доступних контролерів:</p>

        <ul class="landingList" style="list-style-type:square;">
            <#list controllers as controller>
                <li><a class="landingRef" href="/${controller}">${controller?cap_first} controller</a></li>
            </#list>
        </ul>
    </div>
</@pad.page>