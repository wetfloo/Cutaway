package io.wetfloo.cutaway.communication

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Event listener that connects features together,
 * allowing for cross-feature navigation and communication with [Flow]s
 */
class FeatureEventListener @Inject constructor()
