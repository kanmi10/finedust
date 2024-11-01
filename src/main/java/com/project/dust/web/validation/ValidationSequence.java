package com.project.dust.web.validation;

import com.project.dust.web.validation.validationgroup.*;
import jakarta.validation.GroupSequence;

@GroupSequence({NotBlankGroup.class, EmailGroup.class, SizeGroup.class, PatternGroup.class})
public interface ValidationSequence {
}
