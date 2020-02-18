<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/mainRef.ftlh" as main>

<@pad.page "HDD">
    <@main.mainPageRef/>

    <div>
        <p><a href="/hdd/add">Додати новий HDD диск</a></p>
    </div>

    <div>
        <p>Знайти HDD диск за критеріями:</p>
        <form method="get" action="/hdd">
            <label>Модель: <input type="text" name="model" value="${model!}"/></label>
            <label>Обсяг пам'яті: <input type="number" name="memory" min="1" value="${memory!}"/></label>
            <button type="submit">Шукати</button>
        </form>
    </div>

    <#if hdds?has_content>
        <div><p>Список HDD дисків:</p></div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Модель</th>
                <th>Пам'ять(GiB)</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list hdds as hdd>
                <tr>
                    <td>${hdd.id}</td>
                    <td>${hdd.model}</td>
                    <td>${hdd.memory}</td>
                    <td><a href="/hdd/edit/${hdd.id}">Edit</a></td>
                    <td><a href="/hdd/delete/${hdd.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <h3>Не знайдено жодного  HDD диску.</h3>
    </#if>
</@pad.page>