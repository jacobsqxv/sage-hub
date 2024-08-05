CREATE OR REPLACE PROCEDURE create_fk_if_not_exists(
    t_name VARCHAR,
    c_name VARCHAR,
    fkey_name VARCHAR,
    ref_table VARCHAR,
    ref_field VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = lower(c_name)
    ) THEN
        EXECUTE format('ALTER TABLE %I ADD CONSTRAINT %I FOREIGN KEY (%I) REFERENCES %I (%I)',
                       t_name, c_name, fkey_name, ref_table, ref_field);
    END IF;
END;
$$;

-- Add foreign key constraints
CALL create_fk_if_not_exists('_user', 'fk__user_on_role', 'role_id', 'role', 'id');
CALL create_fk_if_not_exists('admin', 'fk_admin_on_user', 'user_id', '_user', 'id');
CALL create_fk_if_not_exists('applicant', 'fk_applicant_on_basic_info', 'basic_info_id', 'basic_info', 'id');
CALL create_fk_if_not_exists('applicant', 'fk_applicant_on_contact_info', 'contact_info_id', 'contact_info', 'id');
CALL create_fk_if_not_exists('applicant', 'fk_applicant_on_emergency_contact', 'emergency_contact_id', 'emergency_contact', 'id');
CALL create_fk_if_not_exists('applicant', 'fk_applicant_on_user', 'user_id', '_user', 'id');
CALL create_fk_if_not_exists('applicant', 'fk_applicant_on_year_of_application', 'year_of_application_id', 'academic_year', 'id');
CALL create_fk_if_not_exists('applicant_program_choices', 'fk_applicant_program_choices_on_applicant', 'applicant_id', 'applicant', 'id');
CALL create_fk_if_not_exists('applicant_program_choices', 'fk_applicant_program_choices_on_program_choices', 'program_choices_id', 'program', 'id');
CALL create_fk_if_not_exists('applicant_result', 'fk_applicant_result_on_applicant', 'applicant_id', 'applicant', 'id');
CALL create_fk_if_not_exists('program', 'fk_program_on_department', 'department_id', 'department', 'id');
CALL create_fk_if_not_exists('program_course', 'fk_program_course_on_course', 'course_id', 'course', 'id');
CALL create_fk_if_not_exists('program_course', 'fk_program_course_on_program', 'program_id', 'program', 'id');
CALL create_fk_if_not_exists('staff', 'fk_staff_on_basic_info', 'basic_info_id', 'basic_info', 'id');
CALL create_fk_if_not_exists('staff', 'fk_staff_on_contact_info', 'contact_info_id', 'contact_info', 'id');
CALL create_fk_if_not_exists('staff', 'fk_staff_on_emergency_contact', 'emergency_contact_id', 'emergency_contact', 'id');
CALL create_fk_if_not_exists('staff', 'fk_staff_on_user', 'user_id', '_user', 'id');
CALL create_fk_if_not_exists('student', 'fk_student_on_basic_info', 'basic_info_id', 'basic_info', 'id');
CALL create_fk_if_not_exists('student', 'fk_student_on_contact_info', 'contact_info_id', 'contact_info', 'id');
CALL create_fk_if_not_exists('student', 'fk_student_on_emergency_contact', 'emergency_contact_id', 'emergency_contact', 'id');
CALL create_fk_if_not_exists('student', 'fk_student_on_user', 'user_id', '_user', 'id');
CALL create_fk_if_not_exists('subject_score', 'fk_subject_score_on_result', 'result_id', 'applicant_result', 'id');
CALL create_fk_if_not_exists('voucher', 'fk_voucher_on_academic_year', 'academic_year_id', 'academic_year', 'id');
