<#import "../parts/htmlPad.ftlh" as pad>
<#import "../parts/errorOutput.ftlh" as error>

<@pad.page "Hardware adding">
    <#if cpuModels?has_content>
        <#if ramModels?has_content>
            <#if ssdModels?has_content>
                <#if displayModels?has_content>
                    <#if hddModels?has_content>
                        <#if gpuModels?has_content>
                            <@error.errorOutput errorMessage!/>

                            <p class="h1">Додати нову збірку:</p>
                            <form method="post" action="/hardware/add" class="form-group needs-validation w-100"
                                  novalidate>
                                <div class="form-row my-2">
                                    <input type="text" class="form-control mr-1 mt-2" name="assemblyName"
                                           value="${RequestParameters.assemblyName!}" placeholder="Нова назва"
                                           required/>
                                    <div class="invalid-feedback">Назва нової моделі не може бути пустою!</div>
                                </div>
                                <div class="form-row my-2">
                                    <select class="custom-select" name="cpuModel">
                                        <option value="" selected disabled>Оберіть модель процесору</option>
                                        <#list cpuModels as cpuModel>
                                            <option>${cpuModel}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-row my-2">
                                    <select class="custom-select" name="gpuModel">
                                        <option value="" selected disabled>Оберіть модель відеокарти</option>
                                        <#list gpuModels as gpuModel>
                                            <option>${gpuModel}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-row my-2">
                                    <select class="custom-select" name="displayModel">
                                        <option value="" selected disabled>Оберіть модель дисплею</option>
                                        <#list displayModels as displayModel>
                                            <option>${displayModel}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-row my-2">
                                    <select class="custom-select" name="ramModel">
                                        <option value="" selected disabled>Оберіть модель оперативної пам'яті</option>
                                        <#list ramModels as ramModel>
                                            <option>${ramModel}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-row my-2">
                                    <select class="custom-select" name="ssdModel">
                                        <option value="" selected disabled>Оберіть модель SSD диску</option>
                                        <#list ssdModels as ssdModel>
                                            <option>${ssdModel}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-row my-2">
                                    <select class="custom-select" name="hddModel">
                                        <option value="" selected disabled>Оберіть модель HDD диску</option>
                                        <#list hddModels as hddModel>
                                            <option>${hddModel}</option>
                                        </#list>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary mt-1 float-right">Додати</button>
                            </form>
                        <#else>
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не знайдено жодної моделі відеокарти.</div>
                                <div class="h3 text-center"><a href="/gpu/add">Додайте нову відеокарту</a>
                                    для створення збірки.
                                </div>
                            </div>
                        </#if>
                    <#else>
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не знайдено жодної моделі HDD диску.</div>
                            <div class="h3 text-center"><a href="/hdd/add">Додайте новий HDD диск</a>
                                для створення збірки.
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="alert alert-warning" role="alert">
                        <div class="h1 text-center">Не знайдено жодної моделі дисплею.</div>
                        <div class="h3 text-center"><a href="/display/add">Додайте новий дисплей</a>
                            для створення збірки.
                        </div>
                    </div>
                </#if>
            <#else>
                <div class="alert alert-warning" role="alert">
                    <div class="h1 text-center">Не знайдено жодної моделі SSD диску.</div>
                    <div class="h3 text-center"><a href="/ssd/add">Додайте новий SSD диск</a>
                        для створення збірки.
                    </div>
                </div>
            </#if>
        <#else>
            <div class="alert alert-warning" role="alert">
                <div class="h1 text-center">Не знайдено жодної моделі оперативної пам'яті.</div>
                <div class="h3 text-center"><a href="/ram/add">Додайте нову оперативну пам'ять</a>
                    для створення збірки.
                </div>
            </div>
        </#if>
    <#else>
        <div class="alert alert-warning" role="alert">
            <div class="h1 text-center">Не знайдено жодної моделі процесору.</div>
            <div class="h3 text-center"><a href="/cpu/add">Додайте новий процесор</a> для створення збірки.</div>
        </div>
    </#if>
</@pad.page>