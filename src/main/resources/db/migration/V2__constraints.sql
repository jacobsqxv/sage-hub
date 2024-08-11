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
CALL create_fk_if_not_exists('application', 'fk_application_on_student', 'applicant_id', 'student', 'id');
CALL create_fk_if_not_exists('application', 'fk_application_on_year_of_application', 'year_of_application_id', 'academic_year', 'id');
CALL create_fk_if_not_exists('application_exam_results', 'fk_application_exam_results_on_application', 'application_id', 'application', 'id');
CALL create_fk_if_not_exists('application_exam_results', 'fk_application_exam_results_on_exam_results', 'exam_results_id', 'exam_result', 'id');
CALL create_fk_if_not_exists('application_program_choices', 'fk_application_program_choices_on_application', 'application_id', 'application', 'id');
CALL create_fk_if_not_exists('application_program_choices', 'fk_application_program_choices_on_program_choices', 'program_choices_id', 'program', 'id');
CALL create_fk_if_not_exists('course_offering', 'fk_course_offering_on_course', 'course_id', 'course', 'id');
CALL create_fk_if_not_exists('course_offering', 'fk_course_offering_on_program', 'program_id', 'program', 'id');
CALL create_fk_if_not_exists('program', 'fk_program_on_department', 'department_id', 'department', 'id');
CALL create_fk_if_not_exists('staff', 'fk_staff_on_user_profile', 'user_profile_id', 'user_profile', 'id');
CALL create_fk_if_not_exists('staff', 'fk_staff_on_emergency_info', 'emergency_info_id', 'emergency_info', 'id');
CALL create_fk_if_not_exists('staff', 'fk_staff_on_user', 'user_id', '_user', 'id');
CALL create_fk_if_not_exists('student', 'fk_student_on_user_profile', 'user_profile_id', 'user_profile', 'id');
CALL create_fk_if_not_exists('student', 'fk_student_on_emergency_info', 'emergency_info_id', 'emergency_info', 'id');
CALL create_fk_if_not_exists('student', 'fk_student_on_user', 'user_id', '_user', 'id');
CALL create_fk_if_not_exists('subject_score', 'fk_subject_score_on_exam_result', 'exam_result_id', 'exam_result', 'id');
CALL create_fk_if_not_exists('voucher', 'fk_voucher_on_academic_year', 'academic_year_id', 'academic_year', 'id');
