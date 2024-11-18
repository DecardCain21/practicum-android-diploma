package ru.practicum.android.diploma.data.dto.vacancy.details

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.vacancy.nested.AddressDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.AreaDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.DepartmentDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.EmployerDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.EmploymentDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.ExperienceDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.KeySkillDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.ProfessionalRoleDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.SalaryDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.ScheduleDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.WorkingItemDto

data class VacancyDetailsDto(
    @SerializedName("address") val address: AddressDto,
    @SerializedName("alternate_url") val alternateUrl: String?,
    @SerializedName("area") val area: AreaDto,
    @SerializedName("contacts") val contacts: Any,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("department") val department: DepartmentDto,
    @SerializedName("description") val description: String?,
    @SerializedName("employer") val employer: EmployerDto?,
    @SerializedName("employment") val employment: EmploymentDto?,
    @SerializedName("experience") val experience: ExperienceDto?,
    @SerializedName("id") val id: String,
    @SerializedName("key_skills") val keySkills: List<KeySkillDto>,
    @SerializedName("name") val name: String?,
    @SerializedName("professional_roles") val professionalRoles: List<ProfessionalRoleDto>,
    @SerializedName("salary") val salary: SalaryDto?,
    @SerializedName("schedule") val schedule: ScheduleDto,
    @SerializedName("working_days") val workingDays: List<WorkingItemDto>,
    @SerializedName("working_time_intervals") val workingTimeIntervals: List<WorkingItemDto>,
    @SerializedName("working_time_modes") val workingTimeModes: List<WorkingItemDto>,
) : Response()
