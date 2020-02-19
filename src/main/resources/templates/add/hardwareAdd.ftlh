<#import "../parts/htmlPad.ftlh" as pad>

<@pad.page "Hardware adding">
    <#if cpuModels?has_content>
        <#if ramModels?has_content>
            <#if ssdModels?has_content>
                <#if displayModels?has_content>
                    <#if hddModels?has_content>
                        <#if gpuModels?has_content>
                            <h2>${errorMessage!}</h2>

                            <h1>Додати нову збірку:</h1>
                            <form method="post" action="/hardware/add">
                                <label>Назва нової збірки: <input type="text" name="assemblyName" value="${assemblyName!}"/></label>
                                <label>Модель процесору нової збірки:
                                    <select name="cpuModel">
                                        <#list cpuModels as cpuModel>
                                            <option>${cpuModel}</option>
                                        </#list>
                                    </select>
                                </label>
                                <label>Модель відеокарти нової збірки:
                                    <select name="gpuModel">
                                        <#list gpuModels as gpuModel>
                                            <option>${gpuModel}</option>
                                        </#list>
                                    </select>
                                </label>
                                <label>Модель дисплею нової збірки:
                                    <select name="displayModel">
                                        <#list displayModels as displayModel>
                                            <option>${displayModel}</option>
                                        </#list>
                                    </select>
                                </label>
                                <label>Модель оперативної пам'яті нової збірки:
                                    <select name="ramModel">
                                        <#list ramModels as ramModel>
                                            <option>${ramModel}</option>
                                        </#list>
                                    </select>
                                </label>
                                <label>Модель SSD диску нової збірки:
                                    <select name="ssdModel">
                                        <#list ssdModels as ssdModel>
                                            <option>${ssdModel}</option>
                                        </#list>
                                    </select>
                                </label>
                                <label>Модель HDD диску нової збірки:
                                    <select name="hddModel">
                                        <#list hddModels as hddModel>
                                            <option>${hddModel}</option>
                                        </#list>
                                    </select>
                                </label>
                                <button type="submit">Додати</button>
                            </form>
                        <#else>
                            <h1>Не знайдено жодної моделі відеокарти.</h1>
                            <p><a href="/gpu/add">Додайте нову відеокарту</a> для створення збірки.</p>
                        </#if>
                    <#else>
                        <h1>Не знайдено жодної моделі HDD диску.</h1>
                        <p><a href="/hdd/add">Додайте новий HDD диск</a> для створення збірки.</p>
                    </#if>
                <#else>
                    <h1>Не знайдено жодної моделі дисплею.</h1>
                    <p><a href="/display/add">Додайте новий дисплей</a> для створення збірки.</p>
                </#if>
            <#else>
                <h1>Не знайдено жодної моделі SSD диску.</h1>
                <p><a href="/ssd/add">Додайте новий SSD диск</a> для створення збірки.</p>
            </#if>
        <#else>
            <h1>Не знайдено жодної моделі оперативної пам'яті.</h1>
            <p><a href="/cpu/add">Додайте нову оперативну пам'ять</a> для створення збірки.</p>
        </#if>
    <#else>
        <h1>Не знайдено жодної моделі процесору.</h1>
        <p><a href="/cpu/add">Додайте новий процесор</a> для створення збірки.</p>
    </#if>
</@pad.page>