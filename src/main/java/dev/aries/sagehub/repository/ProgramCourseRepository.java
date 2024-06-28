package dev.aries.sagehub.repository;

import dev.aries.sagehub.enums.Status;
import dev.aries.sagehub.model.ProgramCourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgramCourseRepository extends JpaRepository<ProgramCourse, Long> {
	@Modifying
	@Query("UPDATE ProgramCourse p SET p.status = :status WHERE p.department.id = :departmentId")
	void updateStatusByDepartmentId(@Param("status") Status status, @Param("departmentId") Long departmentId);
}
